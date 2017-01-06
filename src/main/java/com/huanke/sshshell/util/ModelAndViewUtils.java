package com.huanke.sshshell.util;

import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

public class ModelAndViewUtils {

    public static ModelAndView forward(String url, Map<String, Object> data) {
        ModelAndView mv = new ModelAndView("forward:" + url);
        if (data != null) {
            mv.addAllObjects(data);
        }
        return mv;
    }

    public static ModelAndView forward(String url, String dataName, Object data) {
        ModelAndView mv = new ModelAndView("forward:" + url);
        if (dataName != null) {
            mv.addObject(dataName, data);
        }
        return mv;
    }

    public static ModelAndView toView(String url, Map<String, Object> data) {
        ModelAndView mv = new ModelAndView(url);
        if (data != null) {
            mv.addAllObjects(data);
        }
        return mv;
    }

    public static ModelAndView toView(String url, String dataName, Object data) {
        ModelAndView mv = new ModelAndView(url);
        if (dataName != null) {
            mv.addObject(dataName, data);
        }
        return mv;
    }

    public static ModelAndView redirect(String url, Map<String, Object> data) {
        ModelAndView mv = new ModelAndView("redirect:" + url);
        if (data != null) {
            mv.addAllObjects(data);
        }
        return mv;
    }

}
