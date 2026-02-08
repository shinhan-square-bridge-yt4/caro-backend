package today.caro.api.attendance.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

public record MemberAttendanceStatusGetResponse(
    @Schema(description = "현재 연속 출석 일수", example = "3")
    int currentStreak,

    @Schema(description = "오늘 출석 완료 여부", example = "false")
    boolean isAttendedToday,

    @Schema(description = "최근 연속 출석 기록 리스트")
    List<MemberAttendanceGetResponse> attendanceRecords
) {
    public record MemberAttendanceGetResponse(
        @Schema(description = "출석 순서", example = "1")
        int dayOrder,

        @Schema(description = "출석 날짜", example = "2026-02-09")
        LocalDate attendanceDate,

        @Schema(description = "획득한 포인트", example = "24")
        int points
    ) {}
}
