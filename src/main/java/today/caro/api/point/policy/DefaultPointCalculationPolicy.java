package today.caro.api.point.policy;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 기본 포인트 계산 정책
 * 1km = 1pt
 */
@Component
public class DefaultPointCalculationPolicy implements PointCalculationPolicy {

    private static final BigDecimal POINTS_PER_KM = BigDecimal.ONE;

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
