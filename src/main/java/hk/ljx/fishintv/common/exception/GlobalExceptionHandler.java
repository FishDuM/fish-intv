package hk.ljx.fishintv.common.exception;

import hk.ljx.fishintv.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<Void> handleBusinessException(BusinessException e) {
        log.error("===> 业务异常, 异常码: {}, 异常信息: {}" , e.getCode() ,e.getMessage());
        return Result.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<Void> handleBusinessException(RuntimeException e) {
        log.error("===> 运行异常, 异常信息: {}" ,e.getMessage());
        return Result.error(500, e.getMessage());
    }
}
