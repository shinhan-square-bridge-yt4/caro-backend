package today.caro.api.point.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import today.caro.api.attendance.entity.MemberAttendance;
import today.caro.api.coupon.entity.MemberCoupon;
import today.caro.api.drivingrecord.entity.DrivingRecord;

import java.util.List;

import static today.caro.api.attendance.entity.QMemberAttendance.memberAttendance;
import static today.caro.api.car.entity.QMemberCar.memberCar;
import static today.caro.api.coupon.entity.QMemberCoupon.memberCoupon;
import static today.caro.api.drivingrecord.entity.QDrivingRecord.drivingRecord;
import static today.caro.api.reward.entity.QRewardBrand.rewardBrand;
import static today.caro.api.reward.entity.QRewardCoupon.rewardCoupon;
import static today.caro.api.vehicle.entity.QCarBrand.carBrand;
import static today.caro.api.vehicle.entity.QCarModel.carModel;

@Repository
@RequiredArgsConstructor
public class PointHistoryRepository {

    private final JPAQueryFactory queryFactory;

    public List<DrivingRecord> findDrivingRecordsByMemberId(Long memberId) {
        return queryFactory
            .selectFrom(drivingRecord)
            .join(drivingRecord.memberCar, memberCar).fetchJoin()
            .join(memberCar.model, carModel).fetchJoin()
            .join(carModel.brand, carBrand).fetchJoin()
            .where(
                drivingRecord.member.id.eq(memberId),
                drivingRecord.pointsClaimed.isTrue()
            )
            .orderBy(drivingRecord.createdAt.desc())
            .fetch();
    }

    public List<MemberAttendance> findAttendancesByMemberId(Long memberId) {
        return queryFactory
            .selectFrom(memberAttendance)
            .where(memberAttendance.member.id.eq(memberId))
            .orderBy(memberAttendance.createdAt.desc())
            .fetch();
    }

    public List<MemberCoupon> findCouponExchangesByMemberId(Long memberId) {
        return queryFactory
            .selectFrom(memberCoupon)
            .join(memberCoupon.rewardCoupon, rewardCoupon).fetchJoin()
            .join(rewardCoupon.brand, rewardBrand).fetchJoin()
            .where(memberCoupon.member.id.eq(memberId))
            .orderBy(memberCoupon.createdAt.desc())
            .fetch();
    }

}
