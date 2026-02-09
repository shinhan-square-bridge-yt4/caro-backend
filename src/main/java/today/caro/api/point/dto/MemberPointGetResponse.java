package today.caro.api.point.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record MemberPointGetResponse(

    @Schema(name = "누적 출석 포인트")
    long totalAttendancePoints,

    @Schema(name = "누적 운행 포인트")
    long totalDrivingPoints,

    @Schema(name = "누적 사용 포인트")
    long totalUsedPoints,

    @Schema(name = "현재 가용 포인트")
    long availablePoints

) {
}
