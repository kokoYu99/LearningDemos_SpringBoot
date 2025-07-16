package com.koko.config;

import com.koko.exception.BusinessException;
import com.koko.exception.InvalidParameterException;
import com.koko.exception.ResourceNotFoundException;
import com.koko.utils.Result;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice // 全局控制器增强
@ResponseBody     // 确保返回JSON
public class GlobalExceptionHandler {

    /* 资源找不到异常 */
    @ExceptionHandler(ResourceNotFoundException.class)
    public Result<Object> handleResourceNotFoundException(ResourceNotFoundException e) {
        return Result.fail(e.getCode(), e.getMessage());
    }

    /*捕获所有自定义业务异常*/
    @ExceptionHandler(BusinessException.class)
    public Result<Object> handleBusinessException(BusinessException e) {
        return Result.fail(e.getCode(), e.getMessage());
    }

    /* Validated校验请求体的异常(非空/长度/...) */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<String> errorMessages = result.getAllErrors().stream()
                .map(error -> {
                    if (error instanceof FieldError) {
                        return ((FieldError) error).getField() + ": " + error.getDefaultMessage();
                    }
                    return error.getDefaultMessage();
                })
                .collect(Collectors.toList());

        // 可以返回第一个错误，或者所有错误信息
        String errorMessage = String.join("; ", errorMessages);
        return Result.fail(400, "参数校验失败: " + errorMessage); // 400 Bad Request
    }

    /* Validated校验一般参数的异常 */
    @ExceptionHandler({ConstraintViolationException.class})
    public Result<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        String errorMessage = violations.stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.joining("; "));

        return Result.fail(400, "参数校验失败: " + errorMessage);
    }


    /* 参数校验异常：输入参数在语法上是正确的，但在业务层面上是不合法的 */
    @ExceptionHandler(InvalidParameterException.class)
    public Result<Object> handleInvalidParameterException(InvalidParameterException e) {
        return Result.fail(e.getCode(), e.getMessage());
    }

    /* 捕获所有其他未预料的系统异常 */
    @ExceptionHandler(Exception.class)
    public Result<Object> handleGenericException(Exception e) {
        // 生产环境不建议直接返回e.getMessage()，应记录日志并返回通用错误
        e.printStackTrace(); // 打印堆栈用于调试
        return Result.fail(500, "服务器内部错误，请稍后再试");
    }
}