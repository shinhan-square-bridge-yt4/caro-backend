package today.caro.api.reward.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import today.caro.api.reward.entity.RewardCoupon;

public interface RewardCouponRepository extends JpaRepository<RewardCoupon, Long>, RewardCouponRepositoryCustom {
}
