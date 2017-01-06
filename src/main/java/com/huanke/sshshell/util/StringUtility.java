package com.huanke.sshshell.util;

import java.util.UUID;

public class StringUtility {

    /**
     * 获取GUID字符串
     *
     * @return type String GUID字符串
     * @author 崔凯(Kevin)
     * @createdate 2014年1月18日 上午11:18:29
     */
    public static String getGUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 判断指定value是否是Null或者是空字符串 Kevin 2014-09-15
     * 
     * @param value
     *            待判断字符串
     * @return 操作成功返回True，否则返回False。
     */
    public static boolean IsNullOrEmpty(String value) {
        if (value == null) {
            return true;
        }
        if ("".equals(value)) {
            return true;
        }
        return false;
    }
}
