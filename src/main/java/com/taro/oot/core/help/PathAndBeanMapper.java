package com.taro.oot.core.help;

import java.lang.reflect.Method;

/**
 * @author wangjn
 * @description PathAndBeanMapper
 * @date Created on 2018/3/15.
 */
public class PathAndBeanMapper {
    // view
    private String viewName;
    // 路径对应的Bean
    private Object bean;
    // 路径对应的Method
    private Method method;

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
