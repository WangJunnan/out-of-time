package com.taro.oot.core.request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author wangjn
 * @description
 * @date Created on 2018/3/15.
 */
public class HttpContext {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;

    public HttpContext(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.session = request.getSession();
    }

    public Object getValue(String key) {

        if(request.getAttribute(key) != null){
            return request.getAttribute(key);
        } else {
            String parameter = request.getParameter(key);
            if(parameter!=null){
                return parameter;
            }
        }
		if(session!=null){
            if(session.getAttribute(key)!=null){
                return session.getAttribute(key);
            }
        }
		return null;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public HttpSession getSession() {
        return session;
    }
}
