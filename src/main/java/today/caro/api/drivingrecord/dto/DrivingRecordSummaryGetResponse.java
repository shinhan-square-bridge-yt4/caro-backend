package today.caro.api.drivingrecord.dto;

import java.math.BigDecimal;

public record DrivingRecordSummaryGetResponse(
    BigDecimal totalDistanceKm,
    Long totalEarnedPoints,
    Long totalDrivingCount
) {
}
