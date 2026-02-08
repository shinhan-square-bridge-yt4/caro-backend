package today.caro.api.coupon.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import today.caro.api.common.dto.ApiResponse;
import today.caro.api.common.dto.SuccessCode;
import today.caro.api.config.SwaggerConstants;
import today.caro.api.coupon.dto.MemberCouponCreateRequest;
import today.caro.api.coupon.dto.MemberCouponCreateResponse;
import today.caro.api.coupon.dto.MemberCouponDetailGetResponse;
import today.caro.api.coupon.dto.MemberCouponListGetResponse;
import today.caro.api.coupon.service.MemberCouponService;

@Tag(name = "Coupon", description = "회원 쿠폰 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members/coupons")
public class MemberCouponController {

    private final MemberCouponService memberCouponService;

    @Operation(
        summary = "쿠폰 교환",
        description = "보유 포인트를 사용하여 리워드 쿠폰을 교환합니다.",
        security = @SecurityRequirement(name = SwaggerConstants.BEARER_SCHEME)
    )
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 필요"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음")
    })
    @PostMapping
    public ResponseEntity<ApiResponse<MemberCouponCreateResponse>> createMemberCoupon(
        Authentication authentication,
        @Valid @RequestBody MemberCouponCreateRequest request
    ) {
        Long memberId = Long.parseLong(authentication.getName());
        MemberCouponCreateResponse response = memberCouponService.createMemberCoupon(memberId, request);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.success(SuccessCode.CREATED, response));
    }

    @Operation(
        summary = "보유 쿠폰 목록 조회",
        description = "현재 사용자가 보유한 쿠폰 목록을 조회합니다.",
        security = @SecurityRequirement(name = SwaggerConstants.BEARER_SCHEME)
    )
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 필요"),
    })
    @GetMapping
    public ResponseEntity<ApiResponse<MemberCouponListGetResponse>> getMyCoupons(
        Authentication authentication
    ) {
        Long memberId = Long.parseLong(authentication.getName());
        MemberCouponListGetResponse response = memberCouponService.getMyCoupons(memberId);

        return ResponseEntity
            .ok(ApiResponse.success(SuccessCode.OK, response));
    }

    @Operation(
        summary = "보유 쿠폰 상세 조회",
        description = "현재 사용자가 보유한 쿠폰의 상세 정보를 조회합니다.",
        security = @SecurityRequirement(name = SwaggerConstants.BEARER_SCHEME)
    )
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 필요"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "접근 권한 없음"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음")
    })
    @GetMapping("/{member-coupon-id}")
    public ResponseEntity<ApiResponse<MemberCouponDetailGetResponse>> getMyCouponDetail(
        Authentication authentication,
        @PathVariable(name = "member-coupon-id") Long memberCouponId
    ) {
        Long memberId = Long.parseLong(authentication.getName());
        MemberCouponDetailGetResponse response = memberCouponService.getMyCouponDetail(memberId, memberCouponId);

        return ResponseEntity
            .ok(ApiResponse.success(SuccessCode.OK, response));
    }

}
