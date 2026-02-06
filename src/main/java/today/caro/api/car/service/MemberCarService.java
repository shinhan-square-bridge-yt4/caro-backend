package today.caro.api.car.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import today.caro.api.car.dto.MemberCarGetResponse;
import today.caro.api.common.exception.BusinessException;
import today.caro.api.common.exception.ErrorCode;
import today.caro.api.member.entity.Member;
import today.caro.api.member.repository.MemberRepository;
import today.caro.api.car.dto.MemberCarRegisterRequest;
import today.caro.api.car.dto.MemberCarRegisterResponse;
import today.caro.api.car.entity.MemberCar;
import today.caro.api.car.repository.MemberCarRepository;
import today.caro.api.vehicle.entity.CarModel;
import today.caro.api.vehicle.repository.CarModelRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberCarService {

    private final MemberCarRepository memberCarRepository;
    private final MemberRepository memberRepository;
    private final CarModelRepository carModelRepository;

    @Transactional
    public MemberCarRegisterResponse register(Long memberId, MemberCarRegisterRequest request) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        CarModel model = carModelRepository.findById(request.modelId())
            .orElseThrow(() -> new BusinessException(ErrorCode.MODEL_NOT_FOUND));

        MemberCar memberCar = MemberCar.builder()
            .member(member)
            .model(model)
            .registrationNumber(request.registrationNumber())
            .mileage(request.mileage())
            .build();

        memberCarRepository.save(memberCar);

        return MemberCarRegisterResponse.from(memberCar);
    }

    @Transactional(readOnly = true)
    public List<MemberCarGetResponse> findAllCars(Long memberId) {
        return memberCarRepository.findAllByMemberId(memberId)
            .stream()
            .map(MemberCarGetResponse::from)
            .toList();
    }

    @Transactional
    public void updateRegistrationNumber(Long memberId, Long carId, String registrationNumber) {
        MemberCar car = memberCarRepository.findAllByMemberId(memberId).stream()
            .filter(c -> c.getId().equals(carId))
            .findFirst()
            .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_CAR_ACCESS_DENIED));

        car.updateRegistrationNumber(registrationNumber);
    }

}
