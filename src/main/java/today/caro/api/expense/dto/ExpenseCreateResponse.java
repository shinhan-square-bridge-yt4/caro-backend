package today.caro.api.expense.dto;

import today.caro.api.expense.entity.Expense;

public record ExpenseCreateResponse(
    Long id
) {
    public static ExpenseCreateResponse from(Expense expense) {
        return new ExpenseCreateResponse(expense.getId());
    }
}
