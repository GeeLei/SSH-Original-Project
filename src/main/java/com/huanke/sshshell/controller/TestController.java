package com.huanke.sshshell.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zlg.intf.AjaxJson;

import com.huanke.sshshell.model.TestModel;
import com.huanke.sshshell.service.TestService;

@Controller
@RequestMapping("testController")
public class TestController {

    @Resource
    private TestService testService;

    @RequestMapping("add")
    @ResponseBody
    public AjaxJson add() {
        AjaxJson json = new AjaxJson();
        TestModel model = new TestModel();
        model.setNumber(1);
        json.setObj(testService.persist(model));
        return json;
    }

}
