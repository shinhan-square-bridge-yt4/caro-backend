package today.caro.api.expense.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import today.caro.api.common.exception.BusinessException;
import today.caro.api.common.exception.ErrorCode;
import today.caro.api.expense.dto.ExpenseCreateRequest;
import today.caro.api.expense.dto.ExpenseCreateResponse;
import today.caro.api.expense.entity.Expense;
import today.caro.api.expense.repository.ExpenseRepository;
import today.caro.api.membercar.entity.MemberCar;
import today.caro.api.membercar.repository.MemberCarRepository;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final MemberCarRepository memberCarRepository;

    @Transactional
    public ExpenseCreateResponse createExpense(Long memberId, ExpenseCreateRequest request) {
        MemberCar memberCar = memberCarRepository.findById(request.memberCarId())
            .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_CAR_NOT_FOUND));

        if (!memberCar.getMember().getId().equals(memberId)) {
            throw new BusinessException(ErrorCode.MEMBER_CAR_ACCESS_DENIED);
        }

        Expense expense = Expense.builder()
            .member(memberCar.getMember())
            .memberCar(memberCar)
            .expenseDate(request.expenseDate())
            .amount(request.amount())
            .category(request.category())
            .location(request.location())
            .memo(request.memo())
            .build();

        Expense createdExpense = expenseRepository.save(expense);

        return ExpenseCreateResponse.from(createdExpense);
    }

}
