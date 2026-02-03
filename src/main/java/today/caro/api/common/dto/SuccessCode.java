package today.caro.api.common.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {

    CREATED(HttpStatus.CREATED, "생성이 완료되었습니다.");

    private final HttpStatus httpStatus;
    private final String message;

}
