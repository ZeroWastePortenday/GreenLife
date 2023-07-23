package potenday.zerowaste.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonHandler {

    private boolean success;
    private int code;
    private String message;

    public CommonHandler(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
}

