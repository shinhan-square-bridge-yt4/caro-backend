package today.caro.api.expense.repository;

import static today.caro.api.expense.entity.QExpense.expense;
import static today.caro.api.membercar.entity.QMemberCar.memberCar;
import static today.caro.api.vehicle.entity.QCarModel.carModel;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import today.caro.api.expense.entity.Expense;
import today.caro.api.expense.entity.ExpenseCategory;

@Repository
@RequiredArgsConstructor
public class ExpenseRepositoryImpl implements ExpenseRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Expense> findExpenses(
        Long memberId,
        YearMonth yearMonth,
        LocalDate date,
        ExpenseCategory category,
        LocalDate cursorDate,
        Long cursorId,
        int size
    ) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(expense.member.id.eq(memberId));

        applyDateCondition(builder, yearMonth, date);
        applyCategoryCondition(builder, category);
        applyCursorCondition(builder, cursorDate, cursorId);

        return queryFactory
            .selectFrom(expense)
            .join(expense.memberCar, memberCar).fetchJoin()
            .join(memberCar.model, carModel).fetchJoin()
            .where(builder)
            .orderBy(expense.expenseDate.desc(), expense.id.desc())
            .limit(size)
            .fetch();
    }

    @Override
    public int countExpenses(
        Long memberId,
        YearMonth yearMonth,
        LocalDate date,
        ExpenseCategory category
    ) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(expense.member.id.eq(memberId));

        applyDateCondition(builder, yearMonth, date);
        applyCategoryCondition(builder, category);

        Long count = queryFactory
            .select(expense.count())
            .from(expense)
            .where(builder)
            .fetchOne();

        return count != null ? count.intValue() : 0;
    }

    private void applyDateCondition(BooleanBuilder builder, YearMonth yearMonth, LocalDate date) {
        if (date != null) {
            builder.and(expense.expenseDate.eq(date));
        } else if (yearMonth != null) {
            builder.and(expense.expenseDate.between(
                yearMonth.atDay(1),
                yearMonth.atEndOfMonth()
            ));
        }
    }

    private void applyCategoryCondition(BooleanBuilder builder, ExpenseCategory category) {
        if (category != null) {
            builder.and(expense.category.eq(category));
        }
    }

    private void applyCursorCondition(BooleanBuilder builder, LocalDate cursorDate, Long cursorId) {
        if (cursorDate != null && cursorId != null) {
            builder.and(
                expense.expenseDate.lt(cursorDate)
                    .or(expense.expenseDate.eq(cursorDate).and(expense.id.lt(cursorId)))
            );
        }
    }

}
