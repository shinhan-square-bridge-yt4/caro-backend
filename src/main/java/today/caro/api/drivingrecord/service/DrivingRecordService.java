package today.caro.api.drivingrecord.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import today.caro.api.common.exception.BusinessException;
import today.caro.api.common.exception.ErrorCode;
import today.caro.api.drivingrecord.dto.DrivingRecordCreateRequest;
import today.caro.api.drivingrecord.dto.DrivingRecordCreateResponse;
import today.caro.api.drivingrecord.dto.DrivingRecordListResponse;
import today.caro.api.drivingrecord.dto.DrivingRecordPageResponse;
import today.caro.api.drivingrecord.entity.DrivingRecord;
import today.caro.api.drivingrecord.repository.DrivingRecordRepository;
import today.caro.api.membercar.entity.MemberCar;
import today.caro.api.membercar.repository.MemberCarRepository;
import today.caro.api.point.policy.PointCalculationPolicy;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DrivingRecordService {

    private final DrivingRecordRepository drivingRecordRepository;
    private final MemberCarRepository memberCarRepository;
    private final PointCalculationPolicy pointCalculationPolicy;

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
            .driveDate(request.driveDate())
            .startTime(request.startTime())
            .endTime(request.endTime())
            .distanceKm(request.distanceKm())
            .startLocation(request.startLocation())
            .endLocation(request.endLocation())
            .earnedPoints(earnedPoints)
            .build();

        DrivingRecord saved = drivingRecordRepository.save(record);

        return DrivingRecordCreateResponse.from(saved);
    }

}
