package com.huanke.sshshell.controller;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

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

    @RequestMapping("test")
    @ResponseBody
    public String test() {
        return "ok";
    }

    @RequestMapping("query")
    @ResponseBody
    public AjaxJson queryAll() {
        AjaxJson json = new AjaxJson();
        json.setObj(testService.doQueryAll(TestModel.class));
        return json;
    }

    @RequestMapping("getbyfield")
    @ResponseBody
    public void getByField() {
        Object[] result = testService.getFieldsByProperties(TestModel.class,
                new String[] { "id", "number" }, new String[] { "id" },
                new Object[] { 2 }, null);
        System.out.println(JSONArray.fromObject(result));
    }

    @RequestMapping("querybyfield")
    @ResponseBody
    public void queryByField() {
        List<Object[]> result = testService.queryFieldsByProperties(
                TestModel.class, new String[] { "id", "number" },
                new String[] { "isDelete" }, new Object[] { 0 });
        System.out.println(JSONArray.fromObject(result));
    }

}
