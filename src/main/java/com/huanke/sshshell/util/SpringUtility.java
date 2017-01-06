package com.huanke.sshshell.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class SpringUtility implements ServletContextListener {

    private static WebApplicationContext webApplicationContext;

    public static WebApplicationContext getWebApplicationContext() {
        return webApplicationContext;
    }

    public SpringUtility() {
        super();
    }

    public void contextDestroyed(ServletContextEvent sce) {

    }

    public void contextInitialized(ServletContextEvent sce) {
        webApplicationContext = WebApplicationContextUtils
                .getWebApplicationContext(sce.getServletContext());
    }

}
