package today.caro.api.drivingrecord.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import today.caro.api.member.entity.Member;
import today.caro.api.membercar.entity.MemberCar;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "driving_record")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class DrivingRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_car_id", nullable = false)
    private MemberCar memberCar;

    @Column(nullable = false)
    private LocalDate driveDate;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Column(nullable = false, precision = 10, scale = 1)
    private BigDecimal distanceKm;

    @Column(nullable = false)
    private String startLocation;

    @Column(nullable = false)
    private String endLocation;

    @Column(nullable = false)
    private Integer earnedPoints;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public DrivingRecord(
        Member member,
        MemberCar memberCar,
        LocalDate driveDate,
        LocalTime startTime,
        LocalTime endTime,
        BigDecimal distanceKm,
        String startLocation,
        String endLocation,
        Integer earnedPoints
    ) {
        this.member = member;
        this.memberCar = memberCar;
        this.driveDate = driveDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.distanceKm = distanceKm;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.earnedPoints = earnedPoints;
    }

}
