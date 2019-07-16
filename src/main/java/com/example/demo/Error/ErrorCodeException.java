package com.example.demo.Error;

public class ErrorCodeException extends RuntimeException implements IErrorCode {
    @Override
    public Integer getCode() {
        return null;
    }

    private Integer code;

    private String message;

    public ErrorCodeException(IErrorCode iErrorCode) {
        this.code = iErrorCode.getCode();
        this.message = iErrorCode.getMessage();
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
