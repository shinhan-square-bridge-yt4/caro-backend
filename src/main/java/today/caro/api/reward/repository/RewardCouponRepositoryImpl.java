package today.caro.api.reward.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import today.caro.api.reward.entity.QRewardBrand;
import today.caro.api.reward.entity.QRewardCoupon;
import today.caro.api.reward.entity.RewardCoupon;
import today.caro.api.reward.type.RewardCategory;
import today.caro.api.reward.type.RewardCouponSortType;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RewardCouponRepositoryImpl implements RewardCouponRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<RewardCoupon> findRewardCoupons(
        RewardCategory category,
        RewardCouponSortType sortType,
        Long cursor,
        int size
    ) {
        QRewardCoupon coupon = QRewardCoupon.rewardCoupon;
        QRewardBrand brand = QRewardBrand.rewardBrand;

        return queryFactory
            .selectFrom(coupon)
            .join(coupon.brand, brand).fetchJoin()
            .where(
                categoryEq(category),
                cursorCondition(cursor)
            )
            .orderBy(orderSpecifiers(sortType))
            .limit(size + 1) // hasNext 판별
            .fetch();
    }

    private BooleanExpression categoryEq(RewardCategory category) {
        return category != null ? QRewardBrand.rewardBrand.category.eq(category) : null;
    }

    private BooleanExpression cursorCondition(Long cursor) {
        return cursor != null ? QRewardCoupon.rewardCoupon.id.lt(cursor) : null;
    }

    private OrderSpecifier<?>[] orderSpecifiers(RewardCouponSortType sortType) {
        QRewardCoupon coupon = QRewardCoupon.rewardCoupon;

        return switch (sortType) {
            case POPULAR -> new OrderSpecifier[]{
                coupon.redeemCount.desc(),
                coupon.id.desc()
            };

            case CHEAP -> new OrderSpecifier[]{
                coupon.requiredPoints.asc(),
                coupon.id.desc()
            };

            case ALL -> new OrderSpecifier[]{
                coupon.id.desc()
            };
        };
    }

}
