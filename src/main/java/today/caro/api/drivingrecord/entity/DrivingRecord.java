package today.caro.api.drivingrecord.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import today.caro.api.car.entity.MemberCar;
import today.caro.api.common.exception.BusinessException;
import today.caro.api.common.exception.ErrorCode;
import today.caro.api.member.entity.Member;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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

    @Column(nullable = false)
    private Integer pendingPoints;

    @Column(nullable = false)
    private Boolean pointsClaimed;

    @Convert(converter = RouteCoordinateConverter.class)
    @Column(columnDefinition = "JSON")
    private List<RouteCoordinate> routeCoordinates;

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
        Integer earnedPoints,
        Integer pendingPoints,
        Boolean pointsClaimed,
        List<RouteCoordinate> routeCoordinates
    ) {
        this.member = member;
        this.memberCar = memberCar;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.distanceKm = distanceKm;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.earnedPoints = earnedPoints;
        this.pendingPoints = pendingPoints;
        this.pointsClaimed = pointsClaimed;
        this.routeCoordinates = routeCoordinates;
    }

    public void claimPoints() {
        if (this.pointsClaimed) {
            throw new BusinessException(ErrorCode.POINTS_ALREADY_CLAIMED);
        }

        if (isClaimExpired()) {
            throw new BusinessException(ErrorCode.POINTS_CLAIM_EXPIRED);
        }

        this.earnedPoints = this.pendingPoints;
        this.pointsClaimed = true;
    }

    public boolean isClaimExpired() {
        return LocalDateTime.now().isAfter(getClaimDeadline());
    }

    public LocalDateTime getClaimDeadline() {
        return endDateTime.toLocalDate().plusDays(1).atStartOfDay();
    }

}
