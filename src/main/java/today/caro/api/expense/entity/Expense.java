package today.caro.api.expense.entity;

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

@Entity
@Table(name = "expense")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Expense {

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
    private LocalDate expenseDate;

    @Column(nullable = false, precision = 10, scale = 0)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExpenseCategory category;

    @Column(nullable = false)
    private String location;

    @Column(length = 255)
    private String memo;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public Expense(
        Member member,
        MemberCar memberCar,
        LocalDate expenseDate,
        BigDecimal amount,
        ExpenseCategory category,
        String location,
        String memo
    ) {
        this.member = member;
        this.memberCar = memberCar;
        this.expenseDate = expenseDate;
        this.amount = amount;
        this.category = category;
        this.location = location;
        this.memo = memo;
    }

}
