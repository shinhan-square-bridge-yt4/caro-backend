package today.caro.api.point.policy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class StandardPointCalculationPolicyTest {

    private final StandardPointCalculationPolicy policy = new StandardPointCalculationPolicy();

    @Test
    @DisplayName("주행 거리 100m당 1pt를 계산한다")
    void calculate_shouldReturn1PointPer100m() {
        // given
        BigDecimal distanceKm = new BigDecimal("15.3");

        // when
        int points = policy.calculate(distanceKm);

        // then
        assertThat(points).isEqualTo(153);
    }

    @Test
    @DisplayName("주행 거리가 0km면 0pt를 반환한다")
    void calculate_shouldReturnZeroForZeroDistance() {
        // given
        BigDecimal distanceKm = BigDecimal.ZERO;

        // when
        int points = policy.calculate(distanceKm);

        // then
        assertThat(points).isEqualTo(0);
    }

    @Test
    @DisplayName("주행 거리가 null이면 0pt를 반환한다")
    void calculate_shouldReturnZeroForNullDistance() {
        // when
        int points = policy.calculate(null);

        // then
        assertThat(points).isEqualTo(0);
    }

    @Test
    @DisplayName("주행 거리가 음수이면 0pt를 반환한다")
    void calculate_shouldReturnZeroForNegativeDistance() {
        // given
        BigDecimal distanceKm = new BigDecimal("-10.0");

        // when
        int points = policy.calculate(distanceKm);

        // then
        assertThat(points).isEqualTo(0);
    }

    @Test
    @DisplayName("소수점 이하는 버림 처리한다")
    void calculate_shouldTruncateDecimalPoints() {
        // given
        BigDecimal distanceKm = new BigDecimal("15.99");

        // when
        int points = policy.calculate(distanceKm);

        // then
        assertThat(points).isEqualTo(159); // 15.99 * 10 = 159.9 -> 159
    }

}
