package today.caro.api.drivingrecord.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import today.caro.api.drivingrecord.entity.DrivingRecord;

public interface DrivingRecordRepository extends JpaRepository<DrivingRecord, Long>, DrivingRecordRepositoryCustom {

    long countByMemberId(Long memberId);

}
