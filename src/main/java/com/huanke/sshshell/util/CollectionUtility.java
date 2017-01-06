package com.huanke.sshshell.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionUtility {

    /**
     * 判断指定的HashMap是否为空
     * 
     * @param hashMap
     *            待判断的HashMap
     * @return 操作成功返回True，否则返回False。
     */
    public static <T, K, V> boolean isNullOrEmpty(HashMap<K, V> hashMap) {
        if (hashMap == null) {
            return true;
        }
        return (hashMap.isEmpty() || (hashMap.size() == 0));
    }

    /**
     * 判断指定的List集合是否为空
     * 
     * @param valueList
     *            待判断的List集合
     * @return 操作成功返回True，否则返回False。
     */
    public static <T> boolean isNullOrEmpty(List<T> valueList) {
        if (valueList == null) {
            return true;
        }
        return valueList.isEmpty() || (valueList.size() == 0);
    }

    /**
     * 判断指定的Map是否为空
     * 
     * @param map
     *            待判断的Map
     * @return 操作成功返回True，否则返回False。
     */
    public static <T, K, V> boolean isNullOrEmpty(Map<K, V> map) {
        if (map == null) {
            return true;
        }
        return (map.isEmpty() || (map.size() == 0));
    }

    /**
     * 判断指定的数组是否为空
     * 
     * @param valueArray
     *            待判断的数组
     * @return 操作成功返回True，否则返回False。
     */
    public static <T> boolean isNullOrEmpty(T[] valueArray) {
        if (valueArray == null) {
            return true;
        }
        return valueArray.length == 0;
    }
}
