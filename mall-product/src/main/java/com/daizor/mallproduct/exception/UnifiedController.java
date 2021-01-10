package com.daizor.mallproduct.exception;

import com.daizor.common.exception.BizCodeEnume;
import com.daizor.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

/**
 * 统一异常处理
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.daizor.mallproduct.controller")
public class UnifiedController {

    /**
     * 参数校验异常
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handleValidatedException(MethodArgumentNotValidException e) {
        log.info("出现异常,异常类型 ： {}", e.getClass());
        BindingResult bindingResult = e.getBindingResult();
        HashMap<String, String> errorHashMap = new HashMap<>();
        bindingResult.getFieldErrors().forEach((fieldError) -> {
            errorHashMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return R.error(BizCodeEnume.VAILD_EXCEPTION.getCode(), BizCodeEnume.VAILD_EXCEPTION.getMsg()).put("data", errorHashMap);
    }

    /**
     * 其他异常
     */
    @ExceptionHandler(value = Throwable.class)
    public R handleException(Throwable throwable) {
        log.error("错误  : ", throwable);
        return R.error(BizCodeEnume.UNKNOW_EXCEPTION.getCode(), BizCodeEnume.UNKNOW_EXCEPTION.getMsg());
    }
}
