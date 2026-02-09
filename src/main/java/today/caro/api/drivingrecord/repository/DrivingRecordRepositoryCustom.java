package today.caro.api.drivingrecord.repository;

import today.caro.api.drivingrecord.dto.DrivingRecordSummaryGetResponse;
import today.caro.api.drivingrecord.entity.DrivingRecord;

import java.time.LocalDateTime;
import java.util.List;

public interface DrivingRecordRepositoryCustom {

    DrivingRecordSummaryGetResponse findSummaryByMemberId(Long memberId);

    List<DrivingRecord> findByMemberAndMonth(
        Long memberId, LocalDateTime start, LocalDateTime end, Long cursor, int size
    );

    long countByMemberAndMonth(Long memberId, LocalDateTime start, LocalDateTime end);

    List<DrivingRecord> findPendingByMemberId(Long memberId);

}
