/**   
 * 项目名：国康驿站-延续护理系统
 * 包名：    core.util  
 * 文件名：DateFormat.java    
 * 日期：     2015年4月18日-下午4:35:56  
 * Copyright (c) 2015国康驿站-版权所有     
 */
package core.util;

import java.text.SimpleDateFormat;

/**
 * 类名称：DateFormat 类描述： 创建人： 魏康 创建时间：2015年4月18日 下午4:35:56 修改人： 修改时间：2015年4月18日
 * 下午4:35:56 修改备注：
 */
public class DateFormat {

    public static SimpleDateFormat dateFormat = new SimpleDateFormat(
            "yyyy-MM-dd");
    public static SimpleDateFormat dateFormat2 = new SimpleDateFormat(
            "yyyyMMddHHmmss");
    public static SimpleDateFormat dateFormat3 = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm");

}
