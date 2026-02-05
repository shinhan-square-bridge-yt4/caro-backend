package today.caro.api.expense.service;

import org.springframework.stereotype.Service;
import today.caro.api.expense.dto.ExpenseCategoryGetResponse;
import today.caro.api.expense.entity.ExpenseCategory;

import java.util.Arrays;
import java.util.List;

@Service
public class ExpenseCategoryService {

    public List<ExpenseCategoryGetResponse> getExpenseCategories() {
        return Arrays.stream(ExpenseCategory.values())
            .map(ExpenseCategoryGetResponse::from)
            .toList();
    }

}
