package today.caro.api.point.dto;

import java.math.BigDecimal;

public record EstimatedPointGetResponse(
    BigDecimal distanceKm,
    int estimatedPoints
) {
}
