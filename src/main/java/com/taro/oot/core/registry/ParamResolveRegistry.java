package com.taro.oot.core.registry;

import com.taro.core.annotation.Param;
import com.taro.core.resolver.ParamAnnotationResolve;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @author wangjn
 * @description
 * @date Created on 2018/3/15.
 */
@Component
public class ParamResolveRegistry implements Registry, InitializingBean{

    private static final Map<String,Class<?>> registry = new HashMap<>();

    @Override
    public void init() {
        registry.put(Param.class.getName(), ParamAnnotationResolve.class);
    }

    @Override
    public Object getInstance(String key){
        Class clazz = registry.get(key);
        if (clazz != null) {
            try {
                return clazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }
}
