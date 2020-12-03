package com.weapon.xdvideo.exception;

import com.weapon.xdvideo.utils.JsonData;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 异常处理控制器,捕获全局异常
 */
@ControllerAdvice
public class XdExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonData Handler(Exception e){
        if(e instanceof XdException){
            XdException xdException = (XdException)e;
            return JsonData.buildError(xdException.getMessage(),xdException.getCode());
        }else{
            return JsonData.buildError("全局异常，未知错误");
        }
    }
}
