package core.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.huanke.sshshell.constant.BizStates;

public class LoginFilter implements Filter {

    protected FilterConfig filterConfig = null;
    private String redirectURL = null;
    private List<String> notCheckURLList = new ArrayList<String>();
    private List<String> notCheckPageList = new ArrayList<String>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        redirectURL = filterConfig.getInitParameter("redirectURL");
        String notCheckURLListStr = filterConfig
                .getInitParameter("notCheckURLList");
        if (notCheckURLListStr != null) {
            String[] str = notCheckURLListStr.split(";");
            for (int i = 0; i < str.length; i++) {
                if (str[i].endsWith("/**")) {
                    notCheckURLList
                            .add(str[i].substring(0, str[i].length() - 3));
                } else {
                    notCheckPageList.add(str[i]);
                }
            }
        }
    }

    private boolean checkRequestURIIntNotFilterList(HttpServletRequest request) {
        String uri = request.getServletPath()
                + (request.getPathInfo() == null ? "" : request.getPathInfo());
        if (notCheckPageList.contains(uri)) {
            return true;
        } else
            for (String notCheckURL : notCheckURLList) {
                if (uri.startsWith(notCheckURL)) {
                    return true;
                }
            }
        return false;
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
            ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(true);
        if ((!checkRequestURIIntNotFilterList(request))
                && session.getAttribute(BizStates.SESSION_ATTRS.USER_ID) == null) {
            response.sendRedirect(request.getContextPath() + redirectURL);
            return;
        }
        if (request.getMethod().equalsIgnoreCase("get")) {
            request = new GetHttpServletRequestWrapper(request, "UTF-8");
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        notCheckURLList.clear();
        notCheckPageList.clear();
    }

}
