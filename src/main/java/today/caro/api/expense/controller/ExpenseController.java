package today.caro.api.expense.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import today.caro.api.common.dto.ApiResponse;
import today.caro.api.common.dto.SuccessCode;
import today.caro.api.expense.dto.ExpenseCategoryResponse;
import today.caro.api.expense.dto.ExpenseCreateRequest;

import java.util.List;
import today.caro.api.expense.dto.ExpenseCreateResponse;
import today.caro.api.expense.service.ExpenseCategoryService;
import today.caro.api.expense.service.ExpenseService;

@Tag(name = "Expense", description = "지출 내역 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final ExpenseCategoryService expenseCategoryService;

    @Operation(
        summary = "지출 내역 생성",
        description = "새로운 지출 내역을 생성합니다.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "생성 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 실패"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "차량 접근 권한 없음"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "회원을 찾을 수 없음")
    })
    @PostMapping
    public ResponseEntity<ApiResponse<ExpenseCreateResponse>> createExpense(
        Authentication authentication,
        @Valid @RequestBody ExpenseCreateRequest request
    ) {
        Long memberId = Long.parseLong(authentication.getName());
        ExpenseCreateResponse response = expenseService.createExpense(memberId, request);

        return ResponseEntity
            .ok(ApiResponse.success(SuccessCode.CREATED, response));
    }

    @Operation(
        summary = "지출 카테고리 전체 조회", description = "등록된 모든 지출 카테고리를 조회합니다."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<List<ExpenseCategoryResponse>>> getExpenseCategories() {
        List<ExpenseCategoryResponse> response = expenseCategoryService.getExpenseCategories();

        return ResponseEntity
            .ok(ApiResponse.success(SuccessCode.OK, response));
    }

}
