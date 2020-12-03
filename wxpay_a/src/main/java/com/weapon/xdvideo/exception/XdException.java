package com.weapon.xdvideo.exception;

/**
 * 自定义异常类
 */
public class XdException extends RuntimeException {

    /**
     * 状态码
     */
    private Integer code;
    /**
     * 异常消息
     */
    private String message;

    public XdException(){
        super();
    }

    public XdException(int code,String message){
        super(message);
        this.code = code;
        this.message = message;

    }

    public Integer getCode() {
        return code;
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
