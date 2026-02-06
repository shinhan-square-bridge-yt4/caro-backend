package today.caro.api.drivingrecord.repository;

import today.caro.api.drivingrecord.dto.DrivingRecordSummaryGetResponse;

public interface DrivingRecordRepositoryCustom {

    DrivingRecordSummaryGetResponse findSummaryByMemberId(Long memberId);

}
