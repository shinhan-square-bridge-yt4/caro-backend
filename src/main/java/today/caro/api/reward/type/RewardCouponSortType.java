package today.caro.api.reward.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RewardCouponSortType {

    ALL("전체"),
    POPULAR("인기"),
    CHEAP("가격 낮은 순");

    private final String description;

}
