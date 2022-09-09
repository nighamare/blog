package com.lycoris.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.util.List;

/**
 * @author lycoris
 * @version 1.0
 * @date 2022/8/26 13:38
 */

public class RedisUtil {


    public static String listToJson(List<Object> list){
        if (list == null ) {
            return null;
        }else {
            return JSON.toJSONString(list);
        }

    }
    public static JSONArray jsonToList(String json){
        if (json == null ) {
            return null;
        }else {
            return JSON.parseArray(json);
        }
    }





}
