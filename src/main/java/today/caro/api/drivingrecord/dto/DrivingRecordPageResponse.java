package today.caro.api.drivingrecord.dto;

import java.util.List;

public record DrivingRecordPageResponse(
    List<DrivingRecordListResponse> records,
    Long nextCursor,
    boolean hasNext
) {
    public static DrivingRecordPageResponse of(List<DrivingRecordListResponse> records, int requestedSize) {
        boolean hasNext = records.size() == requestedSize;

        Long nextCursor = hasNext && !records.isEmpty()
            ? records.get(records.size() - 1).id()
            : null;

        return new DrivingRecordPageResponse(records, nextCursor, hasNext);
    }
}
