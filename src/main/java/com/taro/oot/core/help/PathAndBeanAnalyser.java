package com.taro.oot.core.help;

import org.springframework.context.ApplicationContext;

/**
 * @author wangjn
 * @description
 * @date Created on 2018/3/15.
 */
public class PathAndBeanAnalyser {

    private PathAndBeanResolver pathAndBeanResolver;

    public PathAndBeanAnalyser(ApplicationContext applicationContext){
        this.pathAndBeanResolver = new PathAndBeanResolver(applicationContext);
    }

    public PathAndBeanMapper analyse(String path) {
        return pathAndBeanResolver.getBeanByPath(path);
    }
}
