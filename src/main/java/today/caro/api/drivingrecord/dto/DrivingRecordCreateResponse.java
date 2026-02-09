package today.caro.api.drivingrecord.dto;

import today.caro.api.drivingrecord.entity.DrivingRecord;

public record DrivingRecordCreateResponse(
    Long id,
    int pendingPoints
) {
    public static DrivingRecordCreateResponse from(DrivingRecord record) {
        return new DrivingRecordCreateResponse(
            record.getId(),
            record.getPendingPoints()
        );
    }
}
