package today.caro.api.reward.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import today.caro.api.common.dto.ApiResponse;
import today.caro.api.common.dto.SuccessCode;
import today.caro.api.reward.dto.RewardCategoryGetResponse;
import today.caro.api.reward.service.RewardService;

import java.util.List;

@Tag(name = "Reward", description = "리워드 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rewards")
public class RewardController {

    private final RewardService rewardService;

    @Operation(summary = "리워드 카테고리 목록 조회", description = "모든 리워드 카테고리를 조회합니다.")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공")
    })
    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<List<RewardCategoryGetResponse>>> getRewardCategories() {
        List<RewardCategoryGetResponse> response = rewardService.getRewardCategories();

        return ResponseEntity
            .ok(ApiResponse.success(SuccessCode.OK, response));
    }

}
