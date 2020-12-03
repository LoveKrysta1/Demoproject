package com.weapon.online_xdclass.exception;

import com.weapon.online_xdclass.utils.JsonData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 自定义异常处理类
 */
@ControllerAdvice
public class CustomExceptionHandler {

    //日志
    private final static Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody//返回json类数据
    public JsonData handle(Exception e){

        logger.error("[ 系统异常 ]{}",e.getMessage());
        if(e instanceof XDException){//e是属于XDException 继承了runtimeException
            XDException xdException = (XDException)e;
            return  JsonData.buildError(xdException.getCode(),xdException.getMsg());
        }else{
            return JsonData.buildError("全局异常，未知错误");
        }
    }
}
