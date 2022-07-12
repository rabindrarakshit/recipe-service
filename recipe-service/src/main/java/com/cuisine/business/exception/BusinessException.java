package com.cuisine.business.exception;

public class BusinessException extends RuntimeException {
    public Integer code;
    public String details;

    public BusinessException(String message, Integer code, String details) {
        super(message);
        this.code = code;
        this.details = details;
    }
}
