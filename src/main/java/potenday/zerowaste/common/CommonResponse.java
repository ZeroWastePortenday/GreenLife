package potenday.zerowaste.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonResponse {
    SUCCESS(200, "성공하였습니다."), FAIL(-1, "실패하였습니다.");

    private final int code;
    private final String message;

}
