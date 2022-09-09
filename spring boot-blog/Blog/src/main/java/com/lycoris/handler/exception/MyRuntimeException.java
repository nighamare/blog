package com.lycoris.handler.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author tsh
 * @version 1.0
 * @date 2022/7/29 11:47
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MyRuntimeException extends RuntimeException {
    private Integer code;
    private String message;

    public MyRuntimeException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


}
