package today.caro.api.vehicle.dto;

import today.caro.api.vehicle.entity.CarBrand;

public record VehicleBrandGetResponse(
    Long id,
    String name
) {
    public static VehicleBrandGetResponse from(CarBrand brand) {
        return new VehicleBrandGetResponse(brand.getId(), brand.getName());
    }
}
