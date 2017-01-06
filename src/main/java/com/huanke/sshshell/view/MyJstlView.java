package com.huanke.sshshell.view;

import java.io.File;
import java.util.Locale;

import org.springframework.web.servlet.view.JstlView;

public class MyJstlView extends JstlView {

    public boolean checkResource(Locale locale) throws Exception {
        File file = new File(this.getServletContext().getRealPath("/")
                + getUrl());
        return file.exists();// 判断该jsp页面是否存在
    }

}
