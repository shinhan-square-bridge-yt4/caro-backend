package today.caro.api.coupon.dto;

import jakarta.validation.constraints.NotNull;

public record MemberCouponCreateRequest(
    @NotNull Long rewardCouponId
) {
}
