package today.caro.api.point.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PointHistoryType {

    DRIVING("운행 적립"),
    ATTENDANCE("출석 체크"),
    COUPON_EXCHANGE("%s %s"); // ${브랜드 이름} ${상품 이름}

    private final String description;

}
