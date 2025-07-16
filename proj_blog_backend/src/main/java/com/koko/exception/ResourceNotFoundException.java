package com.koko.exception;

// 比如：资源未找到异常
public class ResourceNotFoundException extends BusinessException {
    public ResourceNotFoundException(String message) {
        super(404, message);
    }
}