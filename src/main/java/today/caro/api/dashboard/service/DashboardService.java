package today.caro.api.dashboard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import today.caro.api.attendance.repository.MemberAttendanceRepository;
import today.caro.api.coupon.repository.MemberCouponRepository;
import today.caro.api.dashboard.dto.MemberDashboardGetResponse;
import today.caro.api.drivingrecord.dto.DrivingRecordSummaryGetResponse;
import today.caro.api.drivingrecord.repository.DrivingRecordRepository;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final DrivingRecordRepository drivingRecordRepository;
    private final MemberAttendanceRepository memberAttendanceRepository;
    private final MemberCouponRepository memberCouponRepository;

    @Transactional(readOnly = true)
    public MemberDashboardGetResponse getMyDashboard(Long memberId) {
        DrivingRecordSummaryGetResponse summary = drivingRecordRepository.findSummaryByMemberId(memberId);

        long totalAttendancePoints = memberAttendanceRepository.findTotalPointsByMemberId(memberId);
        long totalDrivingPoints = summary.totalEarnedPoints();
        long totalUsedPoints = memberCouponRepository.findTotalUsedPointsByMemberId(memberId);
        long availablePoints = totalAttendancePoints + totalDrivingPoints - totalUsedPoints;

        long totalDrivingRecordCount = drivingRecordRepository.countByMemberId(memberId);

        return new MemberDashboardGetResponse(
            summary.totalDistanceKm(),
            availablePoints,
            totalDrivingRecordCount
        );
    }

}
