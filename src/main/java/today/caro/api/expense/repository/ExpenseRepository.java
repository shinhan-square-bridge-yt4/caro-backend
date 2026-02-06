package today.caro.api.expense.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import today.caro.api.expense.entity.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long>, ExpenseRepositoryCustom {
}
