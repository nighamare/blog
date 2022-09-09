package com.lycoris.handler;

import com.lycoris.handler.exception.MyRuntimeException;
import com.lycoris.model.HttpResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author tsh
 * @version 1.0
 * @date 2022/7/29 10:42
 */
@ControllerAdvice
public class MyExceptionHandler {


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public HttpResult handleException(Exception e) {
        e.printStackTrace();
        String tokenMessage = "Token无效";
        if (e.getMessage().contains(tokenMessage)) {
            return HttpResult.create(400400, "登录失效，请重新登陆！");
        } else {
            return HttpResult.create(200101, "系统繁忙！请稍后再试！");
        }
    }

    @ExceptionHandler(MyRuntimeException.class)
    @ResponseBody
    public HttpResult handleMyRuntimeException(MyRuntimeException e) {
        e.printStackTrace();
        return HttpResult.create(e.getCode(), e.getMessage());
    }

}
