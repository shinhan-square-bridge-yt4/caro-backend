package today.caro.api.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import today.caro.api.common.dto.ApiResponse;
import today.caro.api.common.dto.EmptyData;
import today.caro.api.common.dto.SuccessCode;
import today.caro.api.config.SwaggerConstants;
import today.caro.api.member.dto.ProfileGetResponse;
import today.caro.api.member.dto.ProfileUpdateRequest;
import today.caro.api.member.service.MemberService;

@Tag(name = "Profile", description = "프로필 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/profiles")
public class MemberProfileController {

    private final MemberService memberService;

    @Operation(
        summary = "내 프로필 조회",
        description = "현재 사용자의 프로필 정보를 조회합니다.",
        security = @SecurityRequirement(name = SwaggerConstants.BEARER_SCHEME)
    )
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 필요")
    })
    @GetMapping
    public ResponseEntity<ApiResponse<ProfileGetResponse>> getProfile(Authentication authentication) {
        Long memberId = Long.parseLong(authentication.getName());
        ProfileGetResponse response = memberService.getProfile(memberId);

        return ResponseEntity
            .ok(ApiResponse.success(SuccessCode.OK, response));
    }

    @Operation(
        summary = "내 프로필 수정",
        description = "현재 사용자의 프로필 정보를 수정합니다.",
        security = @SecurityRequirement(name = SwaggerConstants.BEARER_SCHEME)
    )
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 필요"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "접근 권한 없음"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음")
    })
    @PatchMapping
    public ResponseEntity<ApiResponse<EmptyData>> updateProfile(
        Authentication authentication, ProfileUpdateRequest request
    ) {
        Long memberId = Long.parseLong(authentication.getName());
        memberService.updateProfile(memberId, request);

        return ResponseEntity
            .ok(ApiResponse.success(SuccessCode.OK));
    }

}
