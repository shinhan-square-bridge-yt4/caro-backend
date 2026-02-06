package today.caro.api.member.dto;

import today.caro.api.member.entity.Member;
import today.caro.api.membercar.entity.MemberCar;

public record ProfileGetResponse(
    String name,
    String email,
    PrimaryCarInfo primaryCar
) {
    public record PrimaryCarInfo(
        Long id,
        String brandName,
        String modelName,
        String variant,
        String registrationNumber
    ) {
        public static PrimaryCarInfo from(MemberCar car) {
            return new PrimaryCarInfo(
                car.getId(),
                car.getModel().getBrand().getName(),
                car.getModel().getName(),
                car.getModel().getVariant(),
                car.getRegistrationNumber()
            );
        }
    }

    public static ProfileGetResponse of(Member member, MemberCar car) {
        if (car == null) {
            return new ProfileGetResponse(
                member.getName(),
                member.getEmail(),
                null
            );
        }

        return new ProfileGetResponse(
            member.getName(),
            member.getEmail(),
            PrimaryCarInfo.from(car)
        );
    }
}
