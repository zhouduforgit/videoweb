package com.duyi.video.dto;

/**
 * 定义返回数据格式规范
 */
public class ResponseResult<T> {
    // 1--> 成功  0-->失败   3-->不合法

    //code描述字符串内容
    //1--> 成功  0-->失败   3-->不合法
    private int code;
    private String message;
    private T date;

    public ResponseResult() {
    }

    public ResponseResult(int code, String message, T date) {
        this.code = code;
        this.message = message;
        this.date = date;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getDate() {
        return date;
    }

    public void setDate(T date) {
        this.date = date;
    }
}
