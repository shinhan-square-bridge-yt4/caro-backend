package today.caro.api.dashboard.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import today.caro.api.common.dto.ApiResponse;
import today.caro.api.common.dto.SuccessCode;
import today.caro.api.config.SwaggerConstants;
import today.caro.api.dashboard.dto.MemberDashboardGetResponse;
import today.caro.api.dashboard.service.DashboardService;

@Tag(name = "Dashboard", description = "마이페이지 대시보드 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members/dashboard")
public class DashBoardController {

    private final DashboardService dashboardService;

    @Operation(
        summary = "마이페이지 대시보드 조회",
        description = "총 운행 거리, 가용 포인트, 총 운행 기록 수를 조회합니다.",
        security = @SecurityRequirement(name = SwaggerConstants.BEARER_SCHEME)
    )
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 필요")
    })
    @GetMapping
    public ResponseEntity<ApiResponse<MemberDashboardGetResponse>> getMyDashboard(
        Authentication authentication
    ) {
        Long memberId = Long.parseLong(authentication.getName());
        MemberDashboardGetResponse response = dashboardService.getMyDashboard(memberId);

        return ResponseEntity
            .ok(ApiResponse.success(SuccessCode.OK, response));
    }

}
