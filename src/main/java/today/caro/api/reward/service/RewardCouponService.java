package today.caro.api.reward.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import today.caro.api.reward.dto.RewardCouponGetResponse;
import today.caro.api.reward.dto.RewardCouponPageGetResponse;
import today.caro.api.reward.entity.RewardCoupon;
import today.caro.api.reward.repository.RewardCouponRepository;
import today.caro.api.reward.type.RewardCategory;
import today.caro.api.reward.type.RewardCouponSortType;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RewardCouponService {

    private final RewardCouponRepository rewardCouponRepository;

    @Transactional(readOnly = true)
    public RewardCouponPageGetResponse getRewardCoupons(
        RewardCategory category,
        RewardCouponSortType sortType,
        Long cursor,
        int size
    ) {
        List<RewardCoupon> coupons = rewardCouponRepository.findRewardCoupons(category, sortType, cursor, size);

        boolean hasNext = coupons.size() > size;

        if (hasNext) {
            coupons.remove(size); // size + 1 중 마지막 제거
        }

        List<RewardCouponGetResponse> responses = coupons.stream()
            .map(RewardCouponGetResponse::from)
            .toList();

        Long nextCursor = hasNext
            ? responses.get(responses.size() - 1).id()
            : null;

        return new RewardCouponPageGetResponse(responses, nextCursor, hasNext);
    }

}
