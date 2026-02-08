package today.caro.api.reward.dto;

import today.caro.api.reward.type.RewardCategory;

public record RewardCategoryGetResponse(
    String category,
    String categoryLabel
) {
    public static RewardCategoryGetResponse from(RewardCategory category) {
        return new RewardCategoryGetResponse(category.name(), category.getDescription());
    }
}
