package com.taro.oot.core.resolver;

import com.taro.core.annotation.Param;
import com.taro.core.request.HttpContext;
import java.lang.annotation.Annotation;

/**
 * @author wangjn
 * @description
 * @date Created on 2018/3/16.
 */
public class ParamAnnotationResolve implements ParamResolve{

    private HttpContext context;

    private Param param;

    private String paramName;

    @Override
    public void init(HttpContext context) {
        this.context = context;
    }

    @Override
    public Object resolve(Class parameterClass, Annotation annotation) {
        param = (Param)annotation;
        paramName = param.value();
        return context.getValue(paramName);
    }
}
