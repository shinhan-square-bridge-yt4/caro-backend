package today.caro.api.dashboard.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record MemberDashboardGetResponse(

    @Schema(description = "누적 운행 거리 (km)", example = "1234.567")
    BigDecimal totalDistanceKm,

    @Schema(description = "현재 가용 포인트", example = "25500")
    long availablePoints,

    @Schema(description = "누적 운행 기록 수", example = "42")
    long totalDrivingRecordCount

) {
}
