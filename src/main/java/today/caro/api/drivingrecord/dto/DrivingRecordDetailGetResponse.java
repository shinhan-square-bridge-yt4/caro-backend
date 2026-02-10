package today.caro.api.drivingrecord.dto;

import today.caro.api.drivingrecord.entity.DrivingRecord;
import today.caro.api.drivingrecord.entity.RouteCoordinate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record DrivingRecordDetailGetResponse(
    Long id,
    LocalDateTime startDateTime,
    LocalDateTime endDateTime,
    BigDecimal distanceKm,
    String startLocation,
    String endLocation,
    String vehicleBrandName,
    String vehicleModelName,
    String vehicleVariantName,
    int earnedPoints,
    List<RouteCoordinateGetResponse> routeCoordinates
) {
    public static DrivingRecordDetailGetResponse from(DrivingRecord record) {
        List<RouteCoordinateGetResponse> coordinates = record.getRouteCoordinates() == null
            ? null
            : record.getRouteCoordinates().stream()
                .map(RouteCoordinateGetResponse::from)
                .toList();

        return new DrivingRecordDetailGetResponse(
            record.getId(),
            record.getStartDateTime(),
            record.getEndDateTime(),
            record.getDistanceKm(),
            record.getStartLocation(),
            record.getEndLocation(),
            record.getMemberCar().getModel().getBrand().getName(),
            record.getMemberCar().getModel().getName(),
            record.getMemberCar().getModel().getVariant(),
            record.getEarnedPoints(),
            coordinates
        );
    }

    public record RouteCoordinateGetResponse(
        BigDecimal lat,
        BigDecimal lng,
        Long timestamp
    ) {
        public static RouteCoordinateGetResponse from(RouteCoordinate coordinate) {
            return new RouteCoordinateGetResponse(
                coordinate.lat(),
                coordinate.lng(),
                coordinate.timestamp()
            );
        }
    }
}
