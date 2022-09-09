package com.lycoris.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author lycoris
 * @version 1.0
 * @date 2022/8/25 15:32
 */
public class CommonUtil {
    private static final  Logger  logger = LoggerFactory.getLogger(CommonUtil.class);
    public static InputStream base64ToInputStream(String base64Str){
        ByteArrayInputStream stream = null;
        try {
            byte[] bytes = Base64.getDecoder().decode(base64Str);
            stream = new ByteArrayInputStream(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("base64转输入流失败："+e.getMessage());

        }
        return stream;
    }
    /**
     * @Description 集合元素为空判断
     * @param  pObj obj
     * @return boolean
     * @author lycoris
     * @creed: Talk is cheap,show me the code
     * @date 2022/8/25 15:50
     */
    public static boolean isEmpty(Object pObj) {
        if (pObj == null) {
            return true;
        }
        if (pObj == "") {
            return true;
        }
        if (pObj instanceof String) {
            return ((String) pObj).length() == 0;
        } else if (pObj instanceof Collection) {
            return ((Collection) pObj).size() == 0;
        } else if (pObj instanceof Map) {
            return ((Map) pObj).size() == 0;
        }
        return false;
    }

//    为空判断
    /**
     * 判断字符串是否为空
     * */
    public static boolean isNull(String str){
        if(str!=null){
            str = str.trim();
        }
        return str == null || str.isEmpty();
    }

    /**
     * 判断List数组是否为空
     * */
    public static boolean isNull(List<?> list){
        return list == null || list.isEmpty();
    }

    /**
     * 判断Integer数组是否为空
     * */
    public static boolean isNull(Integer[] arr){
        return arr==null || arr.length<1;
    }

    /**
     * 判断整数是否为空(ID)
     * */
    public static boolean isNull(Integer num){
        return num==null || num<1;
    }

    /**
     * 判断Double是否为空(金额)
     * */
    public static boolean isNull(Double num){
        return num==null || num<1;
    }
    public static boolean isNull(Long num){
        return num==null ;
    }



}
