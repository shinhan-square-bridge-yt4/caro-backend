package today.caro.api.car.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import today.caro.api.member.entity.Member;
import today.caro.api.vehicle.entity.CarModel;

import java.time.LocalDateTime;

@Entity
@Table(name = "member_car")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class MemberCar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id", nullable = false)
    private CarModel model;

    @Column(nullable = false)
    private String registrationNumber;

    @Column(nullable = false)
    private Integer mileage;

    @Column(name = "is_primary", nullable = false)
    private boolean isPrimary = false;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public MemberCar(Member member, CarModel model, String registrationNumber, Integer mileage) {
        this.member = member;
        this.model = model;
        this.registrationNumber = registrationNumber;
        this.mileage = mileage != null ? mileage : 10000;
    }

    public void setPrimary(boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    public void updateRegistrationNumber(String registrationNumber) {
        if (registrationNumber != null && !registrationNumber.isBlank()) {
            this.registrationNumber = registrationNumber;
        }
    }

}
