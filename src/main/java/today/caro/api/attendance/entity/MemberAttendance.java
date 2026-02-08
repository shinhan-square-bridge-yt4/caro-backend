package today.caro.api.attendance.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import today.caro.api.member.entity.Member;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "member_attendance",
    uniqueConstraints = @UniqueConstraint(columnNames = {"member_id", "attendance_date"})
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class MemberAttendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column(name = "attendance_date", nullable = false)
    private LocalDate attendanceDate;

    @Column(nullable = false)
    private int streak;

    @Column(nullable = false)
    private int points;

    @Column(nullable = false)
    private boolean isBonusDay;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public MemberAttendance(Member member, LocalDate attendanceDate, int streak, int points, boolean isBonusDay) {
        this.member = member;
        this.attendanceDate = attendanceDate;
        this.streak = streak;
        this.points = points;
        this.isBonusDay = isBonusDay;
    }

}
