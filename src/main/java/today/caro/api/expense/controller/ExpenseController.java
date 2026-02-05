package today.caro.api.expense.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import today.caro.api.common.dto.ApiResponse;
import today.caro.api.common.dto.SuccessCode;
import today.caro.api.expense.dto.ExpenseCreateRequest;
import today.caro.api.expense.dto.ExpenseCreateResponse;
import today.caro.api.expense.service.ExpenseService;

@Tag(name = "Expense", description = "지출 내역 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

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

}
