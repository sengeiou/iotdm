package com.aibaixun.iotdm.config;

import com.aibaixun.basic.exception.BaseException;
import com.aibaixun.basic.result.BaseResultCode;
import com.aibaixun.basic.result.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/14
 */
@Configuration
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger("ExceptionHandler");

    public GlobalExceptionHandler() {
    }

    
    @ExceptionHandler({BaseException.class})
    public ResponseEntity<JsonResult<Void>> baseExceptionHandler(BaseException e) {
        JsonResult<Void> jsonResult = JsonResult.failed(e.getErrorCode(), e.getMessage());
        LOGGER.warn("ExceptionHandlerConfiguration--baseExceptionHandler errorCode:{},message:{}", e.getErrorCode(), e.getMessage());
        return ResponseEntity.ok(jsonResult);
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<JsonResult<Void>> AssertExceptionHandler(RuntimeException e) {
        JsonResult<Void> jsonResult = JsonResult.failed(BaseResultCode.BAD_PARAMS, e.getMessage());
        LOGGER.warn("ExceptionHandlerConfiguration--AssertExceptionHandler errorCode:{},message:{}", BaseResultCode.BAD_PARAMS, e.getMessage());
        return ResponseEntity.ok(jsonResult);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<JsonResult<Void>> runtimeExceptionHandler(RuntimeException e) {
        JsonResult<Void> jsonResult = JsonResult.failed(e.getMessage());
        LOGGER.warn("ExceptionHandlerConfiguration--runtimeExceptionHandler,message:{}", e.getMessage());
        return ResponseEntity.ok(jsonResult);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<JsonResult<Void>> validationErrorHandler(MethodArgumentNotValidException ex) {
        String message = (String)ex.getBindingResult().getAllErrors().stream().findFirst().map(DefaultMessageSourceResolvable::getDefaultMessage).orElse("");
        JsonResult<Void> jsonResult = JsonResult.failed(BaseResultCode.BAD_PARAMS, message);
        LOGGER.warn("ExceptionHandlerConfiguration--validationErrorHandler,message:{}", message);
        return ResponseEntity.ok(jsonResult);
    }

    @ExceptionHandler({HttpMessageConversionException.class})
    public ResponseEntity<JsonResult<Void>> parameterTypeException(HttpMessageConversionException e) {
        String message = e.getCause().getLocalizedMessage();
        JsonResult<Void> jsonResult = JsonResult.failed(BaseResultCode.BAD_PARAMS, message);
        LOGGER.warn("ExceptionHandlerConfiguration--parameterTypeException,message:{}", message);
        return ResponseEntity.ok(jsonResult);
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    public ResponseEntity<JsonResult<Void>> parameterTypeException(MissingServletRequestParameterException e) {
        String message = e.getMessage();
        JsonResult<Void> jsonResult = JsonResult.failed(BaseResultCode.BAD_PARAMS, message);
        LOGGER.warn("ExceptionHandlerConfiguration--parameterTypeException,message:{}", message);
        return ResponseEntity.ok(jsonResult);
    }

    @ExceptionHandler({MissingRequestHeaderException.class})
    public ResponseEntity<JsonResult<Void>> parameterTypeException(MissingRequestHeaderException e) {
        String message = e.getMessage();
        JsonResult<Void> jsonResult = JsonResult.failed(BaseResultCode.BAD_PARAMS, message);
        LOGGER.warn("ExceptionHandlerConfiguration--parameterTypeException,message:{}", message);
        return ResponseEntity.ok(jsonResult);
    }

    @ExceptionHandler({NoHandlerFoundException.class})
    public ResponseEntity<JsonResult<Void>> handlerNoHandlerFoundException() {
        JsonResult<Void> jsonResult = JsonResult.failed(BaseResultCode.GENERAL_ERROR, "请求方法找不到");
        return ResponseEntity.ok(jsonResult);
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<JsonResult<Void>> handlerHttpRequestMethodNotSupportedException() {
        JsonResult<Void> jsonResult = JsonResult.failed(BaseResultCode.GENERAL_ERROR, "不允许请求方法");
        return ResponseEntity.ok(jsonResult);
    }

    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    public ResponseEntity<JsonResult<Void>> handlerHttpMediaTypeNotSupportedException() {
        JsonResult<Void> jsonResult = JsonResult.failed(BaseResultCode.GENERAL_ERROR, "MediaType不支持请更换");
        return ResponseEntity.ok(jsonResult);
    }
}
