package today.caro.api.reward.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RewardCategory {

    GAS_STATION("주유소"),
    CAFE("카페"),
    FAST_FOOD("패스트푸드");

    private final String description;

}
