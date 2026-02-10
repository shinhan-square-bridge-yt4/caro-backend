package today.caro.api.drivingrecord.entity;

import java.math.BigDecimal;

public record RouteCoordinate(
    BigDecimal lat,
    BigDecimal lng,
    Long timestamp
) {
}
