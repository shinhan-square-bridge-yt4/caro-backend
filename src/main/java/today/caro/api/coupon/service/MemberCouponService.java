package today.caro.api.coupon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import today.caro.api.common.exception.BusinessException;
import today.caro.api.common.exception.ErrorCode;
import today.caro.api.coupon.dto.*;
import today.caro.api.coupon.util.BarcodeNumberGenerator;
import today.caro.api.drivingrecord.dto.DrivingRecordSummaryGetResponse;
import today.caro.api.drivingrecord.repository.DrivingRecordRepository;
import today.caro.api.member.entity.Member;
import today.caro.api.member.repository.MemberRepository;
import today.caro.api.coupon.entity.MemberCoupon;
import today.caro.api.coupon.repository.MemberCouponRepository;
import today.caro.api.reward.entity.RewardCoupon;
import today.caro.api.reward.repository.RewardCouponRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberCouponService {

    private final MemberRepository memberRepository;
    private final RewardCouponRepository rewardCouponRepository;
    private final MemberCouponRepository memberCouponRepository;
    private final DrivingRecordRepository drivingRecordRepository;

    @Transactional
    public MemberCouponCreateResponse createMemberCoupon(Long memberId, MemberCouponCreateRequest request) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        RewardCoupon rewardCoupon = rewardCouponRepository.findById(request.rewardCouponId())
            .orElseThrow(() -> new BusinessException(ErrorCode.REWARD_COUPON_NOT_FOUND));

        DrivingRecordSummaryGetResponse summary = drivingRecordRepository.findSummaryByMemberId(memberId);

        long totalEarnedPoints = summary.totalEarnedPoints();
        long totalUsedPoints = memberCouponRepository.findTotalUsedPointsByMemberId(memberId);
        long availablePoints = totalEarnedPoints - totalUsedPoints;

        if (availablePoints < rewardCoupon.getRequiredPoints()) {
            throw new BusinessException(ErrorCode.INSUFFICIENT_POINTS);
        }

        MemberCoupon memberCoupon = MemberCoupon.builder()
            .member(member)
            .rewardCoupon(rewardCoupon)
            .usedPoints(rewardCoupon.getRequiredPoints())
            .barcodeNumber(BarcodeNumberGenerator.generate())
            .expiredAt(LocalDateTime.now().plusDays(90))
            .build();

        memberCouponRepository.save(memberCoupon);

        rewardCoupon.incrementRedeemCount();

        return new MemberCouponCreateResponse(memberCoupon.getId());
    }

    @Transactional(readOnly = true)
    public MemberCouponListGetResponse getMyCoupons(Long memberId) {
        List<MemberCouponGetResponse> coupons = memberCouponRepository.findActiveCouponsByMemberId(memberId);
        return new MemberCouponListGetResponse(coupons.size(), coupons);
    }

    @Transactional(readOnly = true)
    public MemberCouponDetailGetResponse getMyCouponDetail(Long memberId, Long memberCouponId) {
        MemberCoupon memberCoupon = memberCouponRepository.findWithRewardCouponAndBrandById(memberCouponId)
            .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_COUPON_NOT_FOUND));

        if (!memberCoupon.getMember().getId().equals(memberId)) {
            throw new BusinessException(ErrorCode.MEMBER_COUPON_ACCESS_DENIED);
        }

        RewardCoupon rewardCoupon = memberCoupon.getRewardCoupon();

        return new MemberCouponDetailGetResponse(
            rewardCoupon.getBrand().getName(),
            rewardCoupon.getItemName(),
            memberCoupon.getBarcodeNumber(),
            memberCoupon.getExpiredAt(),
            memberCoupon.getCreatedAt(),
            rewardCoupon.getImageUrl()
        );
    }

}
