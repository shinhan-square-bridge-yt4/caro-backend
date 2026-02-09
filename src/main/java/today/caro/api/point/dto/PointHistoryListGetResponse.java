package today.caro.api.point.dto;

import java.util.List;

public record PointHistoryListGetResponse(
    long totalCount,
    List<PointHistoryGetResponse> histories
) {
}
