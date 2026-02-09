package today.caro.api.drivingrecord.repository;

import static today.caro.api.drivingrecord.entity.QDrivingRecord.drivingRecord;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import today.caro.api.drivingrecord.dto.DrivingRecordSummaryGetResponse;
import today.caro.api.drivingrecord.entity.DrivingRecord;

@Repository
@RequiredArgsConstructor
public class DrivingRecordRepositoryImpl implements DrivingRecordRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public DrivingRecordSummaryGetResponse findSummaryByMemberId(Long memberId) {
        return queryFactory
            .select(Projections.constructor(DrivingRecordSummaryGetResponse.class,
                drivingRecord.distanceKm.sum().coalesce(BigDecimal.ZERO),
                drivingRecord.earnedPoints.sum().coalesce(0).longValue()
            ))
            .from(drivingRecord)
            .where(drivingRecord.member.id.eq(memberId))
            .fetchOne();
    }

    @Override
    public List<DrivingRecord> findByMemberAndMonth(
        Long memberId, LocalDateTime start, LocalDateTime end, Long cursor, int size
    ) {
        return queryFactory
            .selectFrom(drivingRecord)
            .where(
                commonConditions(memberId, start, end), // 공통 조건
                ltCursorId(cursor)                      // 커서 조건
            )
            .orderBy(drivingRecord.id.desc())
            .limit(size)
            .fetch();
    }

    @Override
    public long countByMemberAndMonth(Long memberId, LocalDateTime start, LocalDateTime end) {
        Long count = queryFactory
            .select(drivingRecord.count())
            .from(drivingRecord)
            .where(commonConditions(memberId, start, end)) // 동일한 조건 사용
            .fetchOne();

        return count != null ? count : 0L;
    }

    @Override
    public List<DrivingRecord> findPendingByMemberId(Long memberId) {
        LocalDateTime deadlineCutoff = LocalDate.now().atStartOfDay(); // 오늘 자정

        return queryFactory
            .selectFrom(drivingRecord)
            .where(
                drivingRecord.member.id.eq(memberId),
                drivingRecord.pointsClaimed.isFalse(), // 포인트를 수령하지 않은 기록만
                drivingRecord.endDateTime.goe(deadlineCutoff) // 오늘 자정 이후 운행 종료된 기록만
            )
            .orderBy(drivingRecord.endDateTime.asc()) // 운행 종료 시간 기준 오름차순 정렬
            .fetch();
    }

    private BooleanBuilder commonConditions(Long memberId, LocalDateTime start, LocalDateTime end) {
        return new BooleanBuilder()
            .and(drivingRecord.member.id.eq(memberId))
            .and(drivingRecord.createdAt.between(start, end));
    }

    private BooleanExpression ltCursorId(Long cursorId) {
        return cursorId == null ? null : drivingRecord.id.lt(cursorId);
    }

}
