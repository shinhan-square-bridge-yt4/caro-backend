package today.caro.api.point.policy;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 표준 포인트 계산 정책
 * 100m = 1pt (1km = 10pt)
 */
@Primary
@Component
public class StandardPointCalculationPolicy implements PointCalculationPolicy {

    private static final BigDecimal POINTS_PER_KM = BigDecimal.TEN;

    @Override
    public int calculate(BigDecimal distanceKm) {
        if (distanceKm == null || distanceKm.compareTo(BigDecimal.ZERO) <= 0) {
            return 0;
        }

        return distanceKm.multiply(POINTS_PER_KM)
            .setScale(0, RoundingMode.DOWN)
            .intValue();
    }

}
