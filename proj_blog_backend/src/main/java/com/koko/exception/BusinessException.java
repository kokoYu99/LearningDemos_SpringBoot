package com.koko.exception;

// 比如：业务操作失败异常
public class BusinessException extends RuntimeException {
    private int code; // 错误码，可以根据业务定义

    public BusinessException(String message) {
        super(message);
        this.code = 500; // 默认业务错误码
    }

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

