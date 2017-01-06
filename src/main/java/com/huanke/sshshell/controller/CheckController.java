package com.huanke.sshshell.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zlg.intf.ResultUtils;

@Controller
@RequestMapping("/test")
public class CheckController {

    // 处理体检单元添加
    @RequestMapping("/he")
    @ResponseBody
    public Object test() {
        return ResultUtils.success("You get it");
    }

}