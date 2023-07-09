package potenday.zerowaste.common;

import org.springframework.stereotype.Service;

@Service("responseService")
public class ResponseService {

    // 한개의 객체
    public <T> SingleResult<T> getSingleResult(final T data) {
        final SingleResult<T> result = new SingleResult<>();
        result.setData(data);
        setSuccessResult(result);
        return result;
    }

    // API 요청 성공 시 200 OK
    private void setSuccessResult(final CommonResult result) {
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMessage(CommonResponse.SUCCESS.getMessage());
    }

}