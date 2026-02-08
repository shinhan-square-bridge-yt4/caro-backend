package today.caro.api.reward.dto;

import today.caro.api.reward.entity.RewardCoupon;

public record RewardCouponGetResponse(
    Long id,
    String brandName,
    String itemName,
    String category,
    String categoryLabel,
    int requiredPoints,
    String imageUrl,
    int redeemCount
) {
    public static RewardCouponGetResponse from(RewardCoupon rewardCoupon) {
        return new RewardCouponGetResponse(
            rewardCoupon.getId(),
            rewardCoupon.getBrand().getName(),
            rewardCoupon.getItemName(),
            rewardCoupon.getBrand().getCategory().name(),
            rewardCoupon.getBrand().getCategory().getDescription(),
            rewardCoupon.getRequiredPoints(),
            rewardCoupon.getImageUrl(),
            rewardCoupon.getRedeemCount()
        );
    }
}
