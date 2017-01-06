package core.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import core.service.Service;
import core.support.BaseParameter;
import core.support.ExtJSBaseParameter;
import core.support.ListView;
import core.support.QueryResult;
import core.web.CustomDateEditor;

public abstract class ExtJSBaseController<E extends ExtJSBaseParameter> {

    private final static Logger log = Logger
            .getLogger(ExtJSBaseController.class);

    public static final String CMD_EDIT = "edit";
    public static final String CMD_NEW = "new";
    public static final String MODEL = "model";
    protected String idField;
    protected String statusField;
    protected static final String separator = "/";
    protected Service service;
    protected static ObjectMapper mapper = new ObjectMapper();
    protected static JsonFactory factory = mapper.getJsonFactory();

    @RequestMapping(value = { "/list" }, method = {
            org.springframework.web.bind.annotation.RequestMethod.GET,
            org.springframework.web.bind.annotation.RequestMethod.POST })
    public void doList(@ModelAttribute E entity, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        beforeList(entity);
        BaseParameter parameter = entity;
        QueryResult<ExtJSBaseParameter> qr = this.service.doPaginationQuery(
                ExtJSBaseParameter.class,
                parameter);
        ListView<ExtJSBaseParameter> clv = new ListView<ExtJSBaseParameter>();
        clv.setData(qr.getResultList());

        writeJSON(response, clv);
    }

    @RequestMapping({ "/save" })
    public void doSave(E entity, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        ExtJSBaseParameter parameter = entity;
        Date now = new Date();
        if ("edit".equals(parameter.getCmd())) {
            beforeSaveUpdate(entity);
            try {
                BeanUtils.setProperty(entity, "updateTime", now);
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.service.update(entity);
        } else if ("new".equals(parameter.getCmd())) {
            beforeSaveNew(entity);
            try {
                BeanUtils.setProperty(entity, "createTime", now);
                BeanUtils.setProperty(entity, "updateTime", now);
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.service.persist(entity);
        }
        parameter.setCmd("edit");
        parameter.setSuccess(Boolean.valueOf(true));
        writeJSON(response, parameter);
    }

    @RequestMapping({ "/delete" })
    public void doDelete(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("ids") Serializable[] ids) throws IOException {
        this.service.updateByProperties(ExtJSBaseParameter.class, this.idField,
                ids,
                new String[] { this.statusField },
                new Object[] { Integer.valueOf(-1) });
        writeJSON(response, "{success:true}");
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor());
    }

    protected void writeJSON(HttpServletResponse response, String json)
            throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(json);
    }

    protected void writeJSON(HttpServletResponse response, Object obj)
            throws IOException {
        try {
            response.setContentType("text/html;charset=utf-8");
            JsonGenerator responseJsonGenerator = factory.createJsonGenerator(
                    response.getOutputStream(), JsonEncoding.UTF8);
            responseJsonGenerator.writeObject(obj);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    protected void beforeSaveNew(E example) {
    }

    protected void beforeSaveUpdate(E example) {
    }

    protected void beforeList(E example) {
    }
}
