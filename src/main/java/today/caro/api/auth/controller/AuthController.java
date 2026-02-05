package today.caro.api.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import today.caro.api.auth.dto.EmailExistenceCheckResponse;
import today.caro.api.auth.dto.LoginRequest;
import today.caro.api.auth.dto.LoginResponse;
import today.caro.api.auth.dto.TokenReissueRequest;
import today.caro.api.auth.dto.TokenReissueResponse;
import today.caro.api.auth.dto.SignUpRequest;
import today.caro.api.auth.dto.SignUpResponse;
import today.caro.api.auth.service.AuthService;
import today.caro.api.common.dto.ApiResponse;
import today.caro.api.common.dto.EmptyData;
import today.caro.api.common.dto.SuccessCode;
import today.caro.api.config.SwaggerConstants;

@Tag(name = "Auth", description = "인증 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "회원 가입", description = "이메일, 비밀번호, 이름으로 회원 가입 후 토큰을 발급합니다.")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "이메일 중복")
    })
    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse<SignUpResponse>> signUp(@Valid @RequestBody SignUpRequest request) {
        SignUpResponse response = authService.signUp(request);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.success(SuccessCode.CREATED, response));
    }

    @Operation(summary = "로그인", description = "이메일, 비밀번호로 로그인 후 토큰을 발급합니다.")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 실패")
    })
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success(SuccessCode.OK, response));
    }

    @Operation(
        summary = "로그아웃",
        description = "현재 사용자의 리프레시 토큰을 무효화합니다.",
        security = @SecurityRequirement(name = SwaggerConstants.BEARER_SCHEME)
    )
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 필요")
    })
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<EmptyData>> logout(Authentication authentication) {
        Long memberId = Long.parseLong(authentication.getName());
        authService.logout(memberId);

        return ResponseEntity
            .ok(ApiResponse.success(SuccessCode.OK));
    }

    @Operation(summary = "토큰 재발급", description = "리프레시 토큰으로 새로운 액세스 토큰과 리프레시 토큰을 발급합니다.")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "유효하지 않은 토큰")
    })
    @PostMapping("/reissue")
    public ResponseEntity<ApiResponse<TokenReissueResponse>> reissue(@Valid @RequestBody TokenReissueRequest request) {
        TokenReissueResponse response = authService.reissue(request);

        return ResponseEntity
            .ok(ApiResponse.success(SuccessCode.OK, response));
    }

    @Operation(summary = "이메일 중복 검증", description = "회원 가입 전 이메일 중복 여부를 검증합니다.")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공")
    })
    @GetMapping("/email/exists")
    public ResponseEntity<ApiResponse<EmailExistenceCheckResponse>> checkEmailExists(@RequestParam String email) {
        EmailExistenceCheckResponse response = authService.checkEmailExists(email);

        return ResponseEntity
            .ok(ApiResponse.success(SuccessCode.OK, response));
    }

}
