package today.caro.api.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다."),
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 올바르지 않습니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 리프레시 토큰입니다."),
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 올바르지 않습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다."),

    BRAND_NOT_FOUND(HttpStatus.NOT_FOUND, "차량 브랜드를 찾을 수 없습니다."),
    MODEL_NOT_FOUND(HttpStatus.NOT_FOUND, "차량 모델을 찾을 수 없습니다."),

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."),
    MEMBER_CAR_NOT_FOUND(HttpStatus.NOT_FOUND, "등록된 차량을 찾을 수 없습니다."),
    MEMBER_CAR_ACCESS_DENIED(HttpStatus.FORBIDDEN, "해당 차량에 대한 접근 권한이 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;

}
