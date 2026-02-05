package today.caro.api.expense.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import today.caro.api.expense.entity.ExpenseCategory;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExpenseCreateRequest(

    @NotNull(message = "차량 ID는 필수입니다.")
    Long memberCarId,

    @NotNull(message = "지출 날짜는 필수입니다.")
    LocalDate expenseDate,

    @NotNull(message = "금액은 필수입니다.")
    @Positive(message = "금액은 0보다 커야 합니다.")
    BigDecimal amount,

    @NotNull(message = "카테고리는 필수입니다.")
    ExpenseCategory category,

    @NotBlank(message = "위치는 필수입니다.")
    String location,

    String memo

) {
}
