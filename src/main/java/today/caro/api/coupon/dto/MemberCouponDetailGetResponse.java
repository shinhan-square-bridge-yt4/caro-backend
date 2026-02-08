package today.caro.api.coupon.dto;

import java.time.LocalDateTime;

public record MemberCouponDetailGetResponse(
    String brandName,
    String itemName,
    String barcodeNumber,
    LocalDateTime expiredAt,
    LocalDateTime exchangedAt,
    String imageUrl
) {
}
