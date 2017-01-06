package com.huanke.sshshell.interceptor;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class RequestKeepParamInterceptor implements HandlerInterceptor {

    private List<String> exludeParamsList = Collections.EMPTY_LIST;

    // before the actual handler will be executed
    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    // after the handler is executed
    @Override
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        if (modelAndView != null && handler instanceof HandlerMethod) {
            Map<String, String[]> map = request.getParameterMap();
            for (Entry<String, String[]> e : map.entrySet()) {
                String key = e.getKey();
                String[] value = e.getValue();
                if (!exludeParamsList.contains(key)) {
                    if (value.length == 1) {
                        request.setAttribute(key, value[0]);
                    } else {
                        request.setAttribute(key, value);
                    }
                }
            }
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest arg0,
            HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
    }

    public List<String> getExludeParamsList() {
        return exludeParamsList;
    }

    public void setExludeParamsList(List<String> exludeParamsList) {
        this.exludeParamsList = exludeParamsList;
        if (this.exludeParamsList == null) {
            this.exludeParamsList = Collections.EMPTY_LIST;
        }
    }

}
