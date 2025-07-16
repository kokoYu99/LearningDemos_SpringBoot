package com.koko.exception;

// 比如：参数校验异常
public class InvalidParameterException extends BusinessException {
    public InvalidParameterException(String message) {
        super(400, message);
    }
}