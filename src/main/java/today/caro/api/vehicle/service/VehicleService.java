package today.caro.api.vehicle.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import today.caro.api.common.exception.BusinessException;
import today.caro.api.common.exception.ErrorCode;
import today.caro.api.vehicle.dto.VehicleBrandGetResponse;
import today.caro.api.vehicle.dto.VehicleModelGetResponse;
import today.caro.api.vehicle.repository.CarBrandRepository;
import today.caro.api.vehicle.repository.CarModelRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final CarBrandRepository carBrandRepository;
    private final CarModelRepository carModelRepository;

    @Transactional(readOnly = true)
    public List<VehicleBrandGetResponse> getAllBrands() {
        return carBrandRepository.findAll().stream()
            .map(VehicleBrandGetResponse::from)
            .toList();
    }

    @Transactional(readOnly = true)
    public List<VehicleModelGetResponse> getModelsByBrand(Long brandId, String keyword) {
        if (!carBrandRepository.existsById(brandId)) {
            throw new BusinessException(ErrorCode.BRAND_NOT_FOUND);
        }

        if (keyword != null && !keyword.isBlank()) {
            return carModelRepository.findByBrandIdAndKeyword(brandId, keyword).stream()
                .map(VehicleModelGetResponse::from)
                .toList();
        }

        return carModelRepository.findByBrandId(brandId).stream()
            .map(VehicleModelGetResponse::from)
            .toList();
    }

}
