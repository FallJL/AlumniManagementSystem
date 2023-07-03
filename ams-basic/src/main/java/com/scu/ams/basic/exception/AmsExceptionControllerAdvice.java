package com.scu.ams.basic.exception;

import com.scu.common.exception.BizCodeEnum;
import com.scu.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * 集中处理所有异常
 */
@Slf4j
//@ResponseBody
//@ControllerAdvice(basePackages = "com/scu/ams/basic/controller")
//下面的@RestControllerAdvice已经包含了上面两个注解
@RestControllerAdvice(basePackages = "com.scu.ams.basic.controller")
public class AmsExceptionControllerAdvice {

    /**
     * 数据校验错误的异常处理
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handleValidException(MethodArgumentNotValidException e){
        log.error("参数校验错误：{}，异常类型：{}", e.getMessage(), e.getClass());

        BindingResult result = e.getBindingResult(); // 拿到校验结果
        Map<String, String> errorMap = new HashMap<>();
        result.getFieldErrors().forEach((fieldError)->{ // 把每一个检验错误放入map中
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage()); // 错误属性、错误信息
        });

        return R.error(BizCodeEnum.VALID_EXCEPTION.getCode(), BizCodeEnum.VALID_EXCEPTION.getMsg())
                .put("data", errorMap);
    }


    /**
     * Shiro的无权限异常处理
     */
    @ExceptionHandler(value = UnauthorizedException.class)
    public R handleUnauthorizedException(UnauthorizedException e){
        log.error("Shiro 无权限异常：", e);
        return R.error(HttpStatus.SC_UNAUTHORIZED, "没有权限");
    }

    /**
     * Shiro的鉴权失败异常处理
     */
    @ExceptionHandler(value = AuthorizationException.class)
    public R handleAuthorizationException(AuthorizationException e){
        log.error("Shiro 权限认证失败：", e);
        return R.error(HttpStatus.SC_UNAUTHORIZED, "权限认证失败");
    }

    /**
     * 一般异常的处理
     */
    @ExceptionHandler(value = Throwable.class)
    public R handleException(Throwable throwable){
        log.error("异常：", throwable);
        return R.error(BizCodeEnum.UNKNOWN_EXCEPTION.getCode(), BizCodeEnum.UNKNOWN_EXCEPTION.getMsg());
    }
}
