package today.caro.api.expense.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import today.caro.api.common.dto.ApiResponse;
import today.caro.api.common.dto.SuccessCode;
import today.caro.api.config.SwaggerConstants;
import today.caro.api.expense.dto.ExpenseCategoryGetResponse;
import today.caro.api.expense.dto.ExpenseCreateRequest;
import today.caro.api.expense.dto.ExpenseCreateResponse;
import today.caro.api.expense.dto.ExpensePageGetResponse;
import today.caro.api.expense.entity.ExpenseCategory;
import today.caro.api.expense.service.ExpenseCategoryService;
import today.caro.api.expense.service.ExpenseService;

@Tag(name = "Expense", description = "지출 내역 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final ExpenseCategoryService expenseCategoryService;

    @Operation(
        summary = "지출 내역 생성",
        description = "새로운 지출 내역을 생성합니다.",
        security = @SecurityRequirement(name = SwaggerConstants.BEARER_SCHEME)
    )
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 필요"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "접근 권한 없음"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음")
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

    @Operation(summary = "지출 카테고리 목록 조회", description = "등록된 모든 지출 카테고리를 조회합니다.")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공")
    })
    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<List<ExpenseCategoryGetResponse>>> getExpenseCategories() {
        List<ExpenseCategoryGetResponse> response = expenseCategoryService.getExpenseCategories();

        return ResponseEntity
            .ok(ApiResponse.success(SuccessCode.OK, response));
    }

    @Operation(
        summary = "지출 내역 목록 조회",
        description = """
            지출 내역을 조회합니다.
            - date가 있으면 해당 날짜 조회
            - yearMonth가 있으면 월별 조회
            - 둘 다 없으면 전체 조회
            """,
        security = @SecurityRequirement(name = SwaggerConstants.BEARER_SCHEME)
    )
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 필요")
    })
    @GetMapping
    public ResponseEntity<ApiResponse<ExpensePageGetResponse>> getExpenses(
        Authentication authentication,
        @Parameter(description = "조회 월", example = "2026-02")
        @RequestParam(required = false) YearMonth yearMonth,
        @Parameter(description = "조회 날짜", example = "2026-02-06")
        @RequestParam(required = false) LocalDate date,
        @Parameter(description = "카테고리 필터", example = "FUEL")
        @RequestParam(required = false) ExpenseCategory category,
        @RequestParam(required = false) String cursor,
        @RequestParam(defaultValue = "5") int size
    ) {
        Long memberId = Long.parseLong(authentication.getName());
        ExpensePageGetResponse response = expenseService.getExpenses(
            memberId, yearMonth, date, category, cursor, size
        );

        return ResponseEntity
            .ok(ApiResponse.success(SuccessCode.OK, response));
    }

}
