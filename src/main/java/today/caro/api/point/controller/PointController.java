package today.caro.api.point.controller;

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
import today.caro.api.point.dto.MemberPointGetResponse;
import today.caro.api.point.dto.PendingPointGetResponse;
import today.caro.api.point.dto.PointHistoryListGetResponse;
import today.caro.api.point.service.PointHistoryService;

@Tag(name = "Point", description = "포인트 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members/points")
public class PointController {

    private final PointHistoryService pointHistoryService;

    @Operation(
        summary = "포인트 이력 조회",
        description = "현재 사용자의 포인트 적립 및 차감 이력을 조회합니다.",
        security = @SecurityRequirement(name = SwaggerConstants.BEARER_SCHEME)
    )
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 필요")
    })
    @GetMapping("/history")
    public ResponseEntity<ApiResponse<PointHistoryListGetResponse>> getPointHistory(
        Authentication authentication
    ) {
        Long memberId = Long.parseLong(authentication.getName());
        PointHistoryListGetResponse response = pointHistoryService.getPointHistory(memberId);

        return ResponseEntity
            .ok(ApiResponse.success(SuccessCode.OK, response));
    }

    @Operation(
        summary = "보유 포인트 조회",
        description = "현재 사용자의 출석, 운행, 사용 누적 포인트 및 가용 포인트를 조회합니다.",
        security = @SecurityRequirement(name = SwaggerConstants.BEARER_SCHEME)
    )
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 필요")
    })
    @GetMapping
    public ResponseEntity<ApiResponse<MemberPointGetResponse>> getPoints(
        Authentication authentication
    ) {
        Long memberId = Long.parseLong(authentication.getName());
        MemberPointGetResponse response = pointHistoryService.getPoints(memberId);

        return ResponseEntity
            .ok(ApiResponse.success(SuccessCode.OK, response));
    }

    @Operation(
        summary = "미수령 운행 포인트 현황 조회",
        description = "현재 사용자의 미수령 운행 포인트 현황을 조회합니다.",
        security = @SecurityRequirement(name = SwaggerConstants.BEARER_SCHEME)
    )
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 필요")
    })
    @GetMapping("/pending")
    public ResponseEntity<ApiResponse<PendingPointGetResponse>> getPendingPoints(
        Authentication authentication
    ) {
        Long memberId = Long.parseLong(authentication.getName());
        PendingPointGetResponse response = pointHistoryService.getPendingPoints(memberId);

        return ResponseEntity
            .ok(ApiResponse.success(SuccessCode.OK, response));
    }

}
