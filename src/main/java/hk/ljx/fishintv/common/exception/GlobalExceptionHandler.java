package hk.ljx.fishintv.common.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import hk.ljx.fishintv.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static hk.ljx.fishintv.common.exception.ErrorCode.*;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<Void> handleBusinessException(BusinessException e) {
        log.error("===> 业务异常, 异常码: {}, 异常信息: {}" , e.getCode() ,e.getMessage());
        return Result.error(e.getCode(), e.getMessage());
    }

    // =============== SaToken 异常 ===============

    @ExceptionHandler(NotLoginException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<Void> handleBusinessException(NotLoginException e) {
        log.error("===> 未登录异常");
        return Result.error(NOT_LOGIN);
    }

    @ExceptionHandler(NotPermissionException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<Void> handleBusinessException(NotPermissionException e) {
        log.error("===> 无 VIP 权限异常, 异常信息: {}" ,e.getMessage());
        return Result.error(NOT_VIP, e.getMessage());
    }

    @ExceptionHandler(NotRoleException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public Result<Void> handleBusinessException(NotRoleException e) {
        log.error("===> 无管理员权限异常, 异常信息: {}" ,e.getMessage());
        return Result.error(NOT_ADMIN, e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<Void> handleBusinessException(RuntimeException e) {
        log.error("===> 运行异常, 异常信息: {}" ,e.getMessage());
        return Result.error(500, "系统错误");
    }
}
