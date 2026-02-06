package today.caro.api.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import today.caro.api.car.service.MemberCarService;
import today.caro.api.common.exception.BusinessException;
import today.caro.api.common.exception.ErrorCode;
import today.caro.api.member.dto.ProfileGetResponse;
import today.caro.api.member.dto.ProfileUpdateRequest;
import today.caro.api.member.entity.Member;
import today.caro.api.member.repository.MemberRepository;
import today.caro.api.car.entity.MemberCar;
import today.caro.api.car.repository.MemberCarRepository;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberCarRepository memberCarRepository;
    private final MemberCarService memberCarService;

    @Transactional(readOnly = true)
    public ProfileGetResponse getProfile(Long memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        MemberCar car = memberCarRepository.findFirstByMemberId(memberId).orElse(null);

        return ProfileGetResponse.of(member, car);
    }

    @Transactional
    public void updateProfile(Long memberId, ProfileUpdateRequest request) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        // 회원 이름 업데이트
        member.updateName(request.name());

        // 차량 번호 업데이트 위임
        if (request.car() != null) {
            Long carId = request.car().id();
            String registrationNumber = request.car().registrationNumber();

            memberCarService.updateRegistrationNumber(
                memberId,
                carId,
                registrationNumber
            );
        }
    }

}
