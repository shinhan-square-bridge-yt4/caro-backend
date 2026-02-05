package today.caro.api.expense.dto;

import today.caro.api.expense.entity.ExpenseCategory;

public record ExpenseCategoryGetResponse(
    String code,
    String label
) {
    public static ExpenseCategoryGetResponse from(ExpenseCategory category) {
        return new ExpenseCategoryGetResponse(category.name(), category.getDescription());
    }
}
