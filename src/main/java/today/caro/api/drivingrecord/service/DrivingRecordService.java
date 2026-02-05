package today.caro.api.drivingrecord.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import today.caro.api.drivingrecord.dto.DrivingRecordListResponse;
import today.caro.api.drivingrecord.dto.DrivingRecordPageResponse;
import today.caro.api.drivingrecord.entity.DrivingRecord;
import today.caro.api.drivingrecord.repository.DrivingRecordRepository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DrivingRecordService {

    private final DrivingRecordRepository drivingRecordRepository;

    @Transactional(readOnly = true)
    public DrivingRecordPageResponse getDrivingRecords(Long memberId, YearMonth yearMonth, Long cursor, int size) {
        YearMonth targetMonth = yearMonth != null ? yearMonth : YearMonth.now();

        LocalDate startDate = targetMonth.atDay(1);
        LocalDate endDate = targetMonth.atEndOfMonth();

        List<DrivingRecord> records;

        if (cursor != null) {
            records = drivingRecordRepository.findByMemberIdAndMonth(memberId, startDate, endDate, cursor, size);
        } else {
            records = drivingRecordRepository.findByMemberIdAndMonthNoCursor(memberId, startDate, endDate, size);
        }

        List<DrivingRecordListResponse> responseList = records.stream()
            .map(DrivingRecordListResponse::from)
            .toList();

        return DrivingRecordPageResponse.of(responseList, size);
    }

}
