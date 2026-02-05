package today.caro.api.drivingrecord.dto;

import today.caro.api.drivingrecord.entity.DrivingRecord;
import today.caro.api.drivingrecord.util.DrivingRecordFormatter;

public record DrivingRecordListResponse(
    Long id,
    String driveDate,
    String startTime,
    String endTime,
    String distanceKm,
    String startLocation,
    String endLocation,
    String vehicleModelName,
    String vehicleVariantName,
    int earnedPoints
) {
    public static DrivingRecordListResponse from(DrivingRecord record) {
        return new DrivingRecordListResponse(
            record.getId(),
            DrivingRecordFormatter.formatDate(record.getDriveDate()),
            DrivingRecordFormatter.formatTime(record.getStartTime()),
            DrivingRecordFormatter.formatTime(record.getEndTime()),
            DrivingRecordFormatter.formatDistance(record.getDistanceKm()),
            record.getStartLocation(),
            record.getEndLocation(),
            record.getMemberCar().getModel().getName(),
            record.getMemberCar().getModel().getVariant(),
            record.getEarnedPoints()
        );
    }
}
