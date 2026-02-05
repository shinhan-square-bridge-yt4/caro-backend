package today.caro.api.drivingrecord.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record DrivingRecordCreateRequest(

    @NotNull(message = "차량 ID는 필수입니다.")
    Long memberCarId,

    @NotNull(message = "운행 날짜는 필수입니다.")
    LocalDate driveDate,

    @NotNull(message = "출발 시간은 필수입니다.")
    LocalTime startTime,

    @NotNull(message = "도착 시간은 필수입니다.")
    LocalTime endTime,

    @NotNull(message = "주행 거리는 필수입니다.")
    @Positive(message = "주행 거리는 0보다 커야 합니다.")
    BigDecimal distanceKm,

    @NotBlank(message = "출발 위치는 필수입니다.")
    String startLocation,

    @NotBlank(message = "도착 위치는 필수입니다.")
    String endLocation

) {
}
