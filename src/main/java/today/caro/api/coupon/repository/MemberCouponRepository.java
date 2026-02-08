package today.caro.api.coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import today.caro.api.coupon.entity.MemberCoupon;

import java.util.Optional;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long>, MemberCouponRepositoryCustom {

    @Query("""
        SELECT COALESCE(SUM(mc.usedPoints), 0)
        FROM MemberCoupon mc
        WHERE mc.member.id = :memberId
    """)
    long findTotalUsedPointsByMemberId(@Param("memberId") Long memberId);

    @Query("""
        SELECT mc
        FROM MemberCoupon mc
        JOIN FETCH mc.rewardCoupon rc
        JOIN FETCH rc.brand
        WHERE mc.id = :id
    """)
    Optional<MemberCoupon> findWithRewardCouponAndBrandById(@Param("id") Long id);

}
