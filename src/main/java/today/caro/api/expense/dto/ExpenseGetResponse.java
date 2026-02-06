package today.caro.api.expense.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import today.caro.api.expense.entity.Expense;

public record ExpenseGetResponse(
    Long id,
    LocalDate expenseDate,
    String category,
    String categoryLabel,
    BigDecimal amount,
    String location,
    String memo,
    MemberCarInfo memberCar
) {

    public record MemberCarInfo(
        Long id,
        String name,
        String variant
    ) {}

    public static ExpenseGetResponse from(Expense expense) {
        return new ExpenseGetResponse(
            expense.getId(),
            expense.getExpenseDate(),
            expense.getCategory().name(),
            expense.getCategory().getDescription(),
            expense.getAmount(),
            expense.getLocation(),
            expense.getMemo(),
            new MemberCarInfo(
                expense.getMemberCar().getId(),
                expense.getMemberCar().getModel().getName(),
                expense.getMemberCar().getModel().getVariant()
            )
        );
    }

}
