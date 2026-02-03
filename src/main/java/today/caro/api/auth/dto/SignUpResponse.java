package today.caro.api.auth.dto;

public record SignUpResponse(
    Long memberId,
    String accessToken,
    String refreshToken
) {
}
