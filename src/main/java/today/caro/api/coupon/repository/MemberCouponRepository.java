package today.caro.api.coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import today.caro.api.coupon.entity.MemberCoupon;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    @Query("""
        SELECT COALESCE(SUM(mc.usedPoints), 0)
        FROM MemberCoupon mc
        WHERE mc.member.id = :memberId
    """)
    long findTotalUsedPointsByMemberId(@Param("memberId") Long memberId);

}
