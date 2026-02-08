package today.caro.api.reward.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import today.caro.api.common.dto.ApiResponse;
import today.caro.api.common.dto.SuccessCode;
import today.caro.api.reward.dto.RewardCategoryGetResponse;
import today.caro.api.reward.dto.RewardCouponPageGetResponse;
import today.caro.api.reward.service.RewardCouponService;
import today.caro.api.reward.service.RewardService;
import today.caro.api.reward.type.RewardCategory;
import today.caro.api.reward.type.RewardCouponSortType;

import java.util.List;

@Tag(name = "Reward", description = "리워드 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rewards")
public class RewardController {

    private final RewardService rewardService;
    private final RewardCouponService rewardCouponService;

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

    @Operation(summary = "리워드 쿠폰 목록 조회", description = "카테고리 필터링 및 정렬 조건에 따라 리워드 쿠폰 목록을 조회합니다.")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @GetMapping("/coupons")
    public ResponseEntity<ApiResponse<RewardCouponPageGetResponse>> getRewardCoupons(
        @Parameter(
            description = "리워드 카테고리",
            schema = @Schema(allowableValues = {"GAS_STATION", "CAFE", "FAST_FOOD"})
        )
        @RequestParam(required = false) RewardCategory category,
        @Parameter(
            description = "정렬 기준",
            schema = @Schema(allowableValues = {"ALL", "POPULAR", "CHEAP"}))
        @RequestParam(defaultValue = "ALL") RewardCouponSortType sort,
        @RequestParam(required = false) Long cursor,
        @RequestParam(defaultValue = "5") int size
    ) {
        RewardCouponPageGetResponse response = rewardCouponService.getRewardCoupons(category, sort, cursor, size);

        return ResponseEntity
            .ok(ApiResponse.success(SuccessCode.OK, response));
    }

}
