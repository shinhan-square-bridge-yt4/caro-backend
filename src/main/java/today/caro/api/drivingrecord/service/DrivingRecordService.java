package today.caro.api.drivingrecord.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import today.caro.api.common.exception.BusinessException;
import today.caro.api.common.exception.ErrorCode;
import today.caro.api.drivingrecord.dto.*;
import today.caro.api.drivingrecord.entity.DrivingRecord;
import today.caro.api.drivingrecord.repository.DrivingRecordRepository;
import today.caro.api.car.entity.MemberCar;
import today.caro.api.car.repository.MemberCarRepository;
import today.caro.api.point.policy.PointCalculationPolicy;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DrivingRecordService {

    private final DrivingRecordRepository drivingRecordRepository;
    private final MemberCarRepository memberCarRepository;
    private final PointCalculationPolicy pointCalculationPolicy;

    @Transactional(readOnly = true)
    public DrivingRecordPageGetResponse getDrivingRecords(Long memberId, YearMonth yearMonth, Long cursor, int size) {
        YearMonth targetMonth = (yearMonth != null) ? yearMonth : YearMonth.now();

        LocalDateTime start = targetMonth.atDay(1).atStartOfDay();
        LocalDateTime end = targetMonth.atEndOfMonth().atTime(LocalTime.MAX);

        // 운행 기록 리스트 조회
        List<DrivingRecord> records = drivingRecordRepository.findByMemberAndMonth(memberId, start, end, cursor, size);

        // 해당 월 기준 운행 기록 개수 조회
        long monthlyCount = drivingRecordRepository.countByMemberAndMonth(memberId, start, end);

        List<DrivingRecordGetResponse> responseList = records.stream()
            .map(DrivingRecordGetResponse::from)
            .toList();

        return DrivingRecordPageGetResponse.of(responseList, size, monthlyCount);
    }

    @Transactional
    public DrivingRecordCreateResponse createDrivingRecord(Long memberId, DrivingRecordCreateRequest request) {
        MemberCar memberCar = memberCarRepository.findById(request.memberCarId())
            .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_CAR_NOT_FOUND));

        if (!memberCar.getMember().getId().equals(memberId)) {
            throw new BusinessException(ErrorCode.MEMBER_CAR_ACCESS_DENIED);
        }

        int earnedPoints = pointCalculationPolicy.calculate(request.distanceKm());

        DrivingRecord record = DrivingRecord.builder()
            .member(memberCar.getMember())
            .memberCar(memberCar)
            .startDateTime(request.startDateTime())
            .endDateTime(request.endDateTime())
            .distanceKm(request.distanceKm())
            .startLocation(request.startLocation())
            .endLocation(request.endLocation())
            .earnedPoints(earnedPoints)
            .build();

        DrivingRecord saved = drivingRecordRepository.save(record);

        return DrivingRecordCreateResponse.from(saved);
    }

    @Transactional(readOnly = true)
    public DrivingRecordSummaryGetResponse getSummary(Long memberId) {
        return drivingRecordRepository.findSummaryByMemberId(memberId);
    }

}
