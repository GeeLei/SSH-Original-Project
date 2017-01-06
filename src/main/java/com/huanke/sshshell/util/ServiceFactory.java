package com.huanke.sshshell.util;

import org.apache.log4j.Logger;

public class ServiceFactory {
    private static Logger logger = Logger.getLogger(ServiceFactory.class);

    /**
     * 根据服务Id获取服务实例的引用
     * 
     * @param serviceId
     *            服务Id
     * @return
     * @return 获取成功返回服务实例的引用，否则返回Null。
     */

    @SuppressWarnings("unchecked")
    public static <T> T getService(String serviceId) {
        if (StringUtility.IsNullOrEmpty(serviceId)) {
            logger.error("参数serviceId为空");
            return null;
        }

        try {
            return (T) SpringUtility.getWebApplicationContext().getBean(
                    serviceId);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }
}
