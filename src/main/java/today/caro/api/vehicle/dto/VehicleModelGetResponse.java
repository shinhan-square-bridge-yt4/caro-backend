package today.caro.api.vehicle.dto;

import today.caro.api.vehicle.entity.CarModel;

public record VehicleModelGetResponse(
    Long id,
    String name,
    String variant,
    Integer startYear,
    Integer endYear
) {
    public static VehicleModelGetResponse from(CarModel model) {
        return new VehicleModelGetResponse(
            model.getId(),
            model.getName(),
            model.getVariant(),
            model.getStartYear(),
            model.getEndYear()
        );
    }
}
