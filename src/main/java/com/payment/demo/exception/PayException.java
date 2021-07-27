package com.payment.demo.exception;

import lombok.Data;

/**
 * @author Blue_Sky 7/26/21
 */

public class PayException extends RuntimeException{

    /**
     * exception code
     */
    private Integer code;
    /**
     * exception message
     */
    private String msg;

    public PayException(int code,String msg){
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
