package today.caro.api.drivingrecord.dto;

import java.util.List;

public record DrivingRecordPageGetResponse(
    List<DrivingRecordGetResponse> records,
    Long nextCursor,
    boolean hasNext
) {
    public static DrivingRecordPageGetResponse of(List<DrivingRecordGetResponse> records, int requestedSize) {
        boolean hasNext = records.size() == requestedSize;

        Long nextCursor = hasNext && !records.isEmpty()
            ? records.get(records.size() - 1).id()
            : null;

        return new DrivingRecordPageGetResponse(records, nextCursor, hasNext);
    }
}
