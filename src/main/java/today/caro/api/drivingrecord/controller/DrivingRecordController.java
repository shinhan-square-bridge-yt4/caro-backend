package today.caro.api.drivingrecord.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import today.caro.api.common.dto.ApiResponse;
import today.caro.api.common.dto.SuccessCode;
import today.caro.api.config.SwaggerConstants;
import today.caro.api.drivingrecord.dto.DrivingRecordCreateRequest;
import today.caro.api.drivingrecord.dto.DrivingRecordCreateResponse;
import today.caro.api.drivingrecord.dto.DrivingRecordDetailGetResponse;
import today.caro.api.drivingrecord.dto.DrivingRecordPageGetResponse;
import today.caro.api.drivingrecord.dto.DrivingRecordSummaryGetResponse;
import today.caro.api.drivingrecord.dto.DrivingRecordTodayGetResponse;
import today.caro.api.drivingrecord.service.DrivingRecordService;

import java.time.YearMonth;

@Tag(name = "Driving Record", description = "운행 기록 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/driving-records")
public class DrivingRecordController {

    private final DrivingRecordService drivingRecordService;

    @Operation(
        summary = "운행 기록 목록 조회",
        description = "현재 사용자의 운행 기록을 월별로 조회합니다.",
        security = @SecurityRequirement(name = SwaggerConstants.BEARER_SCHEME)
    )
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 필요")
    })
    @GetMapping
    public ResponseEntity<ApiResponse<DrivingRecordPageGetResponse>> getDrivingRecords(
        Authentication authentication,
        @Parameter(example = "2026-02")
        @RequestParam(required = false) YearMonth yearMonth,
        @RequestParam(required = false) Long cursor,
        @RequestParam(defaultValue = "10") int size
    ) {
        Long memberId = Long.parseLong(authentication.getName());
        DrivingRecordPageGetResponse response = drivingRecordService.getDrivingRecords(memberId, yearMonth, cursor, size);

        return ResponseEntity
            .ok(ApiResponse.success(SuccessCode.OK, response));
    }

    @Operation(
        summary = "운행 기록 상세 조회",
        description = "운행 기록의 상세 정보를 조회합니다.",
        security = @SecurityRequirement(name = SwaggerConstants.BEARER_SCHEME)
    )
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 필요"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "접근 권한 없음"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음")
    })
    @GetMapping("/{driving-record-id}")
    public ResponseEntity<ApiResponse<DrivingRecordDetailGetResponse>> getDrivingRecord(
        Authentication authentication,
        @PathVariable(name = "driving-record-id") Long drivingRecordId
    ) {
        Long memberId = Long.parseLong(authentication.getName());
        DrivingRecordDetailGetResponse response = drivingRecordService.getDrivingRecord(memberId, drivingRecordId);

        return ResponseEntity
            .ok(ApiResponse.success(SuccessCode.OK, response));
    }

    @Operation(
        summary = "운행 기록 생성",
        description = "새로운 운행 기록을 생성합니다.",
        security = @SecurityRequirement(name = SwaggerConstants.BEARER_SCHEME)
    )
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 필요"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "접근 권한 없음"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음")
    })
    @PostMapping
    public ResponseEntity<ApiResponse<DrivingRecordCreateResponse>> createDrivingRecord(
        Authentication authentication,
        @Valid @RequestBody DrivingRecordCreateRequest request
    ) {
        Long memberId = Long.parseLong(authentication.getName());
        DrivingRecordCreateResponse response = drivingRecordService.createDrivingRecord(memberId, request);

        return ResponseEntity
            .ok(ApiResponse.success(SuccessCode.CREATED, response));
    }

    @Operation(
        summary = "운행 기록 요약 조회",
        description = "운행 기록 요약을 조회합니다.",
        security = @SecurityRequirement(name = SwaggerConstants.BEARER_SCHEME)
    )
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 필요")
    })
    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<DrivingRecordSummaryGetResponse>> getSummary(
        Authentication authentication
    ) {
        Long memberId = Long.parseLong(authentication.getName());
        DrivingRecordSummaryGetResponse response = drivingRecordService.getSummary(memberId);

        return ResponseEntity
            .ok(ApiResponse.success(SuccessCode.OK, response));
    }

    @Operation(
        summary = "오늘의 운행 기록 조회",
        description = "현재 사용자의 오늘 날짜 운행 기록을 조회합니다.",
        security = @SecurityRequirement(name = SwaggerConstants.BEARER_SCHEME)
    )
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 필요")
    })
    @GetMapping("/today")
    public ResponseEntity<ApiResponse<DrivingRecordTodayGetResponse>> getTodayDrivingRecords(
        Authentication authentication
    ) {
        Long memberId = Long.parseLong(authentication.getName());
        DrivingRecordTodayGetResponse response = drivingRecordService.getTodayDrivingRecords(memberId);

        return ResponseEntity
            .ok(ApiResponse.success(SuccessCode.OK, response));
    }

}
