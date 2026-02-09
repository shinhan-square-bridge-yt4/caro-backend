package today.caro.api.drivingrecord.repository;

import static today.caro.api.drivingrecord.entity.QDrivingRecord.drivingRecord;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import today.caro.api.drivingrecord.dto.DrivingRecordSummaryGetResponse;

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

}
