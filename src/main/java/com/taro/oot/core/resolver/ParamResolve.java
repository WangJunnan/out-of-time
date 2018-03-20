package com.taro.oot.core.resolver;

import com.taro.core.request.HttpContext;
import java.lang.annotation.Annotation;

/**
 * @author wangjn
 * @description
 * @date Created on 2018/3/16.
 */
public interface ParamResolve {

    void init(HttpContext context);

    Object resolve(Class parameterClass, Annotation annotation);

}
