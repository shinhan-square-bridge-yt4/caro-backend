package today.caro.api.drivingrecord.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record DrivingRecordSummaryGetResponse(

    @Schema(name = "누적 운행 거리", example = "0.003")
    BigDecimal totalDistanceKm,

    @Schema(name = "누적 운행 적립 포인트", example = "37")
    Long totalEarnedPoints

) {
}
