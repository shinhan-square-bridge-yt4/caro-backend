package today.caro.api.point.dto;

import today.caro.api.drivingrecord.entity.DrivingRecord;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DrivingDetail(
    LocalDateTime startDateTime,
    LocalDateTime endDateTime,
    BigDecimal distanceKm,
    String carBrandName,
    String carModelName,
    String carVariant
) {
    public static DrivingDetail from(DrivingRecord record) {
        return new DrivingDetail(
            record.getStartDateTime(),
            record.getEndDateTime(),
            record.getDistanceKm(),
            record.getMemberCar().getModel().getBrand().getName(),
            record.getMemberCar().getModel().getName(),
            record.getMemberCar().getModel().getVariant()
        );
    }
}
