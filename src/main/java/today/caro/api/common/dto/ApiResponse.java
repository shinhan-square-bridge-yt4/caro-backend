package today.caro.api.common.dto;

import today.caro.api.common.exception.ErrorCode;

public record ApiResponse<T>(
    String code,
    String message,
    T data
) {

    public static <T> ApiResponse<T> success(SuccessCode successCode, T data) {
        return new ApiResponse<>(successCode.name(), successCode.getMessage(), data);
    }

    public static ApiResponse<EmptyData> error(ErrorCode errorCode, String message) {
        return new ApiResponse<>(errorCode.name(), message, new EmptyData());
    }

    public static ApiResponse<EmptyData> error(ErrorCode errorCode) {
        return error(errorCode, errorCode.getMessage());
    }

}
