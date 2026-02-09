package today.caro.api.attendance.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import today.caro.api.attendance.entity.MemberAttendance;
import today.caro.api.member.entity.Member;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MemberAttendanceRepository extends JpaRepository<MemberAttendance, Long> {

    Optional<MemberAttendance> findTopByMemberOrderByAttendanceDateDesc(Member member);

    boolean existsByMemberIdAndAttendanceDate(Long memberId, LocalDate date);

    @Query("""
        SELECT ma
        FROM MemberAttendance ma
        WHERE ma.member = :member
        ORDER BY ma.attendanceDate DESC
    """)
    List<MemberAttendance> findRecentStreakRecords(@Param("member") Member member, Pageable pageable);

    @Query("""
        SELECT COALESCE(SUM(ma.points), 0)
        FROM MemberAttendance ma
        WHERE ma.member.id = :memberId
    """)
    long findTotalPointsByMemberId(@Param("memberId") Long memberId);

}
