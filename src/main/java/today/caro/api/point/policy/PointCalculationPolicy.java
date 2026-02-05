package today.caro.api.point.policy;

import java.math.BigDecimal;

/**
 * 주행 거리를 포인트로 변환하는 정책 인터페이스
 */
public interface PointCalculationPolicy {

    /**
     * 주행 거리(km)를 포인트로 변환한다.
     *
     * @param distanceKm 주행 거리 (km)
     * @return 획득 포인트
     */
    int calculate(BigDecimal distanceKm);

}
