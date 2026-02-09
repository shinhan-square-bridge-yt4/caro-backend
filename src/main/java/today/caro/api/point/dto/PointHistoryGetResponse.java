package today.caro.api.point.dto;

import today.caro.api.attendance.entity.MemberAttendance;
import today.caro.api.coupon.entity.MemberCoupon;
import today.caro.api.drivingrecord.entity.DrivingRecord;
import today.caro.api.point.type.PointHistoryType;

import java.time.LocalDateTime;

public record PointHistoryGetResponse(
    PointHistoryType type,
    String description,
    int pointChange,
    LocalDateTime date,
    DrivingDetail drivingDetail
) {
    public static PointHistoryGetResponse ofDriving(DrivingRecord record, DrivingDetail detail) {
        return new PointHistoryGetResponse(
            PointHistoryType.DRIVING,
            PointHistoryType.DRIVING.getDescription(),
            record.getEarnedPoints(),
            record.getStartDateTime(),
            detail
        );
    }

    public static PointHistoryGetResponse ofAttendance(MemberAttendance attendance) {
        return new PointHistoryGetResponse(
            PointHistoryType.ATTENDANCE,
            PointHistoryType.ATTENDANCE.getDescription(),
            attendance.getPoints(),
            attendance.getCreatedAt(),
            null
        );
    }

    public static PointHistoryGetResponse ofCoupon(MemberCoupon coupon) {
        String brandName = coupon.getRewardCoupon().getBrand().getName();
        String itemName = coupon.getRewardCoupon().getItemName();
        String description = String.format("%s %s", brandName, itemName);

        return new PointHistoryGetResponse(
            PointHistoryType.COUPON_EXCHANGE,
            description,
            -coupon.getUsedPoints(),
            coupon.getCreatedAt(),
            null
        );
    }
}
