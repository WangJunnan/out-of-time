package com.taro.oot.core.help;

import com.taro.core.annotation.Hub;
import com.taro.core.annotation.Rest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.springframework.context.ApplicationContext;

/**
 * @author wangjn
 * @description
 * @date Created on 2018/3/15.
 */
public class PathAndBeanResolver {

    private ApplicationContext applicationContext;

    private Map<String, PathAndBeanMapper> restPathMap = new HashMap();

    public PathAndBeanResolver (ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        init();
    }

    public PathAndBeanMapper getBeanByPath(String path) {
        return restPathMap.get(path);
    }

    public void init() {
        Map<String, Object> hubObjectMap = applicationContext.getBeansWithAnnotation(Hub.class);
        Set<Entry<String, Object>> hubObjectSet = hubObjectMap.entrySet();
        for (Entry entry : hubObjectSet) {
            Object object = entry.getValue();
            Method[] methods = object.getClass().getMethods();
            for (Method method : methods) {
                Rest rest = method.getAnnotation(Rest.class);
                if (rest == null) {
                    continue;
                }
                String path = rest.value();
                PathAndBeanMapper pathAndBean = new PathAndBeanMapper();
                pathAndBean.setBean(object);
                pathAndBean.setMethod(method);
                pathAndBean.setViewName(object.getClass().getAnnotation(Hub.class).value());
                restPathMap.put(path, pathAndBean);
            }
        }
    }
}
