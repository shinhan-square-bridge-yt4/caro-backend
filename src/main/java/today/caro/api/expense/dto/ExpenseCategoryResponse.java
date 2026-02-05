package today.caro.api.expense.dto;

import today.caro.api.expense.entity.ExpenseCategory;

public record ExpenseCategoryResponse(
    String code,
    String label
) {
    public static ExpenseCategoryResponse from(ExpenseCategory category) {
        return new ExpenseCategoryResponse(category.name(), category.getDescription());
    }
}
