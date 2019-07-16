package com.example.demo.DTO;


import com.example.demo.Error.IErrorCode;

/**
 * Created by codedrinker on 2018/11/25.
 */

public class ResultDTO {
    private Integer status;
    private Object data;
    private String message;

    public static ResultDTO ok(Object data) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setStatus(200);
        resultDTO.setData(data);
        resultDTO.setMessage("请求成功");
        return resultDTO;
    }

    public static ResultDTO fail(String message) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setStatus(400);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO fail(IErrorCode errorCode) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setStatus(errorCode.getCode());
        resultDTO.setMessage(errorCode.getMessage());
        return resultDTO;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
