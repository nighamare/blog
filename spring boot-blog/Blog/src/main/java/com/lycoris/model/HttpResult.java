package com.lycoris.model;

import java.util.HashMap;

/**
 * @author lycoris
 * @version 1.0
 * @date 2022/8/10 16:19
 */
public class HttpResult extends HashMap<String, Object> {
    public HttpResult set(String key, Object value) {
        this.put(key, value);
        return this;
    }

    public static HttpResult create(int code, String message) {
        return new HttpResult().set("code", code).set("msg", message);
    }

}
