package com.payment.demo.exception;

import com.payment.demo.utils.JsonData;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Blue_Sky 7/26/21
 */
@RestControllerAdvice
public class PayExceptionHandler {

    @ExceptionHandler(value = PayException.class)
    public JsonData Handler(PayException e){
        return JsonData.buildFailure(e.getCode(),e.getMsg());
    }
    @ExceptionHandler(value = Exception.class)
    public JsonData GeneralHandler(Exception e){
        return  JsonData.buildFailure("Unknown Error...");
    }
}
