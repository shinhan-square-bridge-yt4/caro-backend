package today.caro.api.point.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import today.caro.api.attendance.entity.MemberAttendance;
import today.caro.api.coupon.entity.MemberCoupon;
import today.caro.api.drivingrecord.entity.DrivingRecord;
import today.caro.api.point.dto.DrivingDetail;
import today.caro.api.point.dto.PointHistoryGetResponse;
import today.caro.api.point.dto.PointHistoryListGetResponse;
import today.caro.api.point.repository.PointHistoryRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PointHistoryService {

    private final PointHistoryRepository pointHistoryRepository;

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

}
