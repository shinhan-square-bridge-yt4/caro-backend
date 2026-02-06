package today.caro.api.expense.dto;

import jakarta.annotation.Nullable;
import java.util.List;
import today.caro.api.expense.util.ExpenseCursor;

public record ExpensePageGetResponse(
    int totalCount,
    List<ExpenseGetResponse> expenses,
    @Nullable String nextCursor,
    boolean hasNext
) {

    public static ExpensePageGetResponse of(
        int totalCount,
        List<ExpenseGetResponse> expenses,
        int requestedSize
    ) {
        boolean hasNext = expenses.size() > requestedSize;

        List<ExpenseGetResponse> trimmed = hasNext
            ? expenses.subList(0, requestedSize)
            : expenses;

        String nextCursor = null;

        if (!trimmed.isEmpty()) {
            ExpenseGetResponse last = trimmed.get(trimmed.size() - 1);
            nextCursor = new ExpenseCursor(last.expenseDate(), last.id()).encode();
        }

        return new ExpensePageGetResponse(totalCount, trimmed, nextCursor, hasNext);
    }

}
