package today.caro.api.reward.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import today.caro.api.reward.dto.RewardCategoryGetResponse;
import today.caro.api.reward.entity.RewardCategory;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RewardService {

    @Transactional(readOnly = true)
    public List<RewardCategoryGetResponse> getRewardCategories() {
        return Arrays.stream(RewardCategory.values())
            .map(RewardCategoryGetResponse::from)
            .toList();
    }

}
