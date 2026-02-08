package today.caro.api.attendance.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import today.caro.api.attendance.dto.MemberAttendResponse;
import today.caro.api.attendance.dto.MemberAttendanceStatusGetResponse;
import today.caro.api.attendance.service.MemberAttendanceService;
import today.caro.api.common.dto.ApiResponse;
import today.caro.api.common.dto.SuccessCode;
import today.caro.api.config.SwaggerConstants;

@Tag(name = "Attendance", description = "출석 체크 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members/attendances")
public class MemberAttendanceController {

    private final MemberAttendanceService memberAttendanceService;

    @Operation(
        summary = "출석 체크",
        description = "현재 사용자의 출석을 기록합니다.",
        security = @SecurityRequirement(name = SwaggerConstants.BEARER_SCHEME)
    )
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 필요"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "리소스 충돌")
    })
    @PostMapping
    public ResponseEntity<ApiResponse<MemberAttendResponse>> attend(
        Authentication authentication
    ) {
        Long memberId = Long.parseLong(authentication.getName());
        MemberAttendResponse response = memberAttendanceService.attend(memberId);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.success(SuccessCode.CREATED, response));
    }

    @Operation(
        summary = "출석 현황 조회",
        description = "현재 스트릭 상태와 이번 스트릭 동안 획득한 포인트 내역을 조회합니다.",
        security = @SecurityRequirement(name = SwaggerConstants.BEARER_SCHEME)
    )
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 필요"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음"),
    })
    @GetMapping("/status")
    public ResponseEntity<ApiResponse<MemberAttendanceStatusGetResponse>> getAttendanceStatus(
        Authentication authentication
    ) {
        Long memberId = Long.parseLong(authentication.getName());
        MemberAttendanceStatusGetResponse response = memberAttendanceService.getAttendanceStatus(memberId);

        return ResponseEntity
            .ok(ApiResponse.success(SuccessCode.OK, response));
    }

}
