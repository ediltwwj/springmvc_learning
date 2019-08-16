package com.springmvc.exception;

/**
 * @author 13967
 * @date 2019/8/16 21:43
 *
 * 自定义异常类
 */
public class SysException extends Exception{

    // 存储错误提示信息
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SysException(String message) {

        this.message = message;
    }
}
