package today.caro.api.point.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import today.caro.api.attendance.entity.MemberAttendance;
import today.caro.api.attendance.repository.MemberAttendanceRepository;
import today.caro.api.common.exception.BusinessException;
import today.caro.api.common.exception.ErrorCode;
import today.caro.api.coupon.entity.MemberCoupon;
import today.caro.api.coupon.repository.MemberCouponRepository;
import today.caro.api.drivingrecord.dto.DrivingRecordSummaryGetResponse;
import today.caro.api.drivingrecord.entity.DrivingRecord;
import today.caro.api.drivingrecord.repository.DrivingRecordRepository;
import today.caro.api.point.dto.DrivingDetail;
import today.caro.api.point.dto.EstimatedPointGetResponse;
import today.caro.api.point.dto.MemberPointGetResponse;
import today.caro.api.point.dto.PendingPointGetResponse;
import today.caro.api.point.dto.PointClaimResponse;
import today.caro.api.point.dto.PointHistoryGetResponse;
import today.caro.api.point.dto.PointHistoryListGetResponse;
import today.caro.api.point.policy.PointCalculationPolicy;
import today.caro.api.point.repository.PointHistoryRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PointHistoryService {

    private final PointHistoryRepository pointHistoryRepository;
    private final MemberAttendanceRepository memberAttendanceRepository;
    private final DrivingRecordRepository drivingRecordRepository;
    private final MemberCouponRepository memberCouponRepository;
    private final PointCalculationPolicy pointCalculationPolicy;

    @Transactional(readOnly = true)
    public PointHistoryListGetResponse getPointHistory(Long memberId) {
        List<DrivingRecord> drivingRecords = pointHistoryRepository.findDrivingRecordsByMemberId(memberId);
        List<MemberAttendance> attendances = pointHistoryRepository.findAttendancesByMemberId(memberId);
        List<MemberCoupon> couponExchanges = pointHistoryRepository.findCouponExchangesByMemberId(memberId);

        List<PointHistoryGetResponse> histories = new ArrayList<>();

        for (DrivingRecord record : drivingRecords) {
            DrivingDetail detail = DrivingDetail.from(record);
            histories.add(PointHistoryGetResponse.ofDriving(record, detail));
        }

        for (MemberAttendance attendance : attendances) {
            histories.add(PointHistoryGetResponse.ofAttendance(attendance));
        }

        for (MemberCoupon coupon : couponExchanges) {
            histories.add(PointHistoryGetResponse.ofCoupon(coupon));
        }

        histories.sort(Comparator.comparing(PointHistoryGetResponse::date).reversed());

        return new PointHistoryListGetResponse(histories.size(), histories);
    }

    @Transactional(readOnly = true)
    public MemberPointGetResponse getPoints(Long memberId) {
        DrivingRecordSummaryGetResponse summary = drivingRecordRepository.findSummaryByMemberId(memberId);

        long totalAttendancePoints = memberAttendanceRepository.findTotalPointsByMemberId(memberId);
        long totalDrivingPoints = summary.totalEarnedPoints();
        long totalUsedPoints = memberCouponRepository.findTotalUsedPointsByMemberId(memberId);
        long availablePoints = totalAttendancePoints + totalDrivingPoints - totalUsedPoints;

        return new MemberPointGetResponse(
            totalAttendancePoints, totalDrivingPoints, totalUsedPoints, availablePoints
        );
    }

    @Transactional(readOnly = true)
    public PendingPointGetResponse getPendingPoints(Long memberId) {
        List<DrivingRecord> pendingRecords = drivingRecordRepository.findPendingByMemberId(memberId);

        int totalPendingPoints = pendingRecords.stream()
            .mapToInt(DrivingRecord::getPendingPoints)
            .sum();

        return new PendingPointGetResponse(totalPendingPoints);
    }

    @Transactional
    public PointClaimResponse claimOldestPendingPoints(Long memberId) {
        List<DrivingRecord> pendingRecords = drivingRecordRepository.findPendingByMemberId(memberId);

        if (pendingRecords.isEmpty()) {
            throw new BusinessException(ErrorCode.NO_PENDING_POINTS);
        }

        DrivingRecord oldest = pendingRecords.get(0);
        oldest.claimPoints();

        int remainingCount = pendingRecords.size() - 1;

        return new PointClaimResponse(oldest.getEarnedPoints(), remainingCount);
    }

    public EstimatedPointGetResponse estimatePoints(BigDecimal distanceKm) {
        int estimatedPoints = pointCalculationPolicy.calculate(distanceKm);
        return new EstimatedPointGetResponse(distanceKm, estimatedPoints);
    }

}
