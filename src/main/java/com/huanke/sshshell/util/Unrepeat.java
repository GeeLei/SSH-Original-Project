package com.huanke.sshshell.util;

import java.util.HashSet;
import java.util.List;

/**
 * 
 * 类名称：Unrepeat 类描述： 去重 创建人： 邹猛 创建时间：2015年9月22日 下午2:27:16 修改人： 修改时间：2015年9月22日
 * 下午2:27:16 修改备注：
 */
public class Unrepeat {
    /*
     * public static List<Object> removeRepeat(List<Object> ls){ HashSet<Object>
     * h = new HashSet<Object>(ls); ls.clear(); ls.addAll(h); return ls; }
     */
    public static List<Long> removeRepeatLong(List<Long> ls) {
        HashSet<Long> h = new HashSet<Long>(ls);
        ls.clear();
        ls.addAll(h);
        return ls;
    }
}
