package today.caro.api.drivingrecord.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import today.caro.api.car.entity.MemberCar;
import today.caro.api.member.entity.Member;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    private LocalDateTime startDateTime;

    @Column(nullable = false)
    private LocalDateTime endDateTime;

    @Column(nullable = false, precision = 15, scale = 3)
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
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        BigDecimal distanceKm,
        String startLocation,
        String endLocation,
        Integer earnedPoints
    ) {
        this.member = member;
        this.memberCar = memberCar;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.distanceKm = distanceKm;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.earnedPoints = earnedPoints;
    }

}
