package today.caro.api.reward.repository;

import today.caro.api.reward.entity.RewardCoupon;
import today.caro.api.reward.type.RewardCategory;
import today.caro.api.reward.type.RewardCouponSortType;

import java.util.List;

public interface RewardCouponRepositoryCustom {

    List<RewardCoupon> findRewardCoupons(
        RewardCategory category,
        RewardCouponSortType sortType,
        Long cursor,
        int size
    );

}
