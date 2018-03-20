package com.taro.oot.core.factory;

import com.taro.core.annotation.Param;
import com.taro.core.registry.ParamResolveRegistry;
import com.taro.core.request.HttpContext;
import com.taro.core.resolver.ParamResolve;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author wangjn
 * @description
 * @date Created on 2018/3/15.
 */
public class ParamResolveFactory {

    private ParamResolveRegistry paramResolveRegistry;

    private HttpContext context;

    private Method method;

    private Object object;

    private Annotation[][] parameterAnnotations;

    private Class[] parameterTypes;

    private int parameterLength;

    public ParamResolveFactory(ParamResolveRegistry resolveRegistry, HttpContext context,
        Method method) {
        this.paramResolveRegistry = resolveRegistry;
        this.context = context;
        this.method = method;
        init();
    }

    public void init() {
        parameterAnnotations = method.getParameterAnnotations();
        parameterTypes = method.getParameterTypes();
        parameterLength = parameterTypes.length;
    }

    public Object[] getMethodParameters() {
        Object[] methodValues = new Object[parameterLength];
        for (int i = 0; i < parameterLength; i++) {
            Class parameterType = parameterTypes[i];
            Annotation[] annotations = parameterAnnotations[i];
            if (annotations != null || annotations.length == 0) {
                if (parameterType == HttpServletRequest.class) {
                    methodValues[i] = context.getRequest();
                }
                if (parameterType == HttpServletResponse.class) {
                    methodValues[i] = context.getResponse();
                }
                if (parameterType == HttpSession.class) {
                    methodValues[i] = context.getSession();
                }
            }
            for (Annotation annotation:annotations) {
                Object resolve = paramResolveRegistry.getInstance(annotation.annotationType().getName());
                if (resolve instanceof ParamResolve) {
                    ((ParamResolve) resolve).init(context);
                    methodValues[i] = ((ParamResolve) resolve).resolve(parameterType, annotation);
                }
            }
        }
        return methodValues;
    }

}
