package today.caro.api.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import today.caro.api.auth.dto.SignUpRequest;
import today.caro.api.auth.dto.SignUpResponse;
import today.caro.api.auth.service.AuthService;
import today.caro.api.common.dto.ApiResponse;
import today.caro.api.common.dto.SuccessCode;

@Tag(name = "Auth", description = "인증 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "회원 가입", description = "이메일, 비밀번호, 이름으로 가입 후 토큰을 발급합니다.")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "회원 가입 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "입력값 검증 실패"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "이메일 중복")
    })
    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse<SignUpResponse>> signUp(@Valid @RequestBody SignUpRequest request) {
        SignUpResponse response = authService.signUp(request);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.success(SuccessCode.CREATED, response));
    }

}
