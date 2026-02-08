package today.caro.api.reward.dto;

import java.util.List;

public record RewardCouponPageGetResponse(
    List<RewardCouponGetResponse> rewardCoupons,
    Long nextCursor,
    boolean hasNext
) {
}
