package today.caro.api.member.dto;

public record ProfileUpdateRequest(
    String name,
    CarUpdateRequest car
) {
    public record CarUpdateRequest(
        Long id,
        String registrationNumber
    ) {}
}
