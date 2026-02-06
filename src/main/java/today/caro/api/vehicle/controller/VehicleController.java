package today.caro.api.vehicle.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import today.caro.api.common.dto.ApiResponse;
import today.caro.api.common.dto.SuccessCode;
import today.caro.api.vehicle.dto.VehicleBrandGetResponse;
import today.caro.api.vehicle.dto.VehicleModelGetResponse;
import today.caro.api.vehicle.service.VehicleService;

import java.util.List;

@Tag(name = "Vehicle", description = "차량 카탈로그 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    @Operation(summary = "차량 브랜드 목록 조회", description = "등록된 모든 차량 브랜드를 조회합니다.")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공")
    })
    @GetMapping("/brands")
    public ResponseEntity<ApiResponse<List<VehicleBrandGetResponse>>> getAllBrands() {
        List<VehicleBrandGetResponse> response = vehicleService.getAllBrands();

        return ResponseEntity
            .ok(ApiResponse.success(SuccessCode.OK, response));
    }

    @Operation(summary = "차량 모델 목록 조회", description = "특정 브랜드의 차량 모델을 조회합니다. 키워드로 필터링할 수 있습니다.")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음")
    })
    @GetMapping("/brands/{brand-id}/models")
    public ResponseEntity<ApiResponse<List<VehicleModelGetResponse>>> getModelsByBrand(
        @PathVariable(name = "brand-id") Long brandId,
        @RequestParam(required = false) String keyword
    ) {
        List<VehicleModelGetResponse> response = vehicleService.getModelsByBrand(brandId, keyword);

        return ResponseEntity
            .ok(ApiResponse.success(SuccessCode.OK, response));
    }

}
