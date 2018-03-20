package com.taro.oot.core.registry;

/**
 * @author wangjn
 * @description
 * @date Created on 2018/3/16.
 */
public interface Registry {

    /**
     * 初始化注册表
     */
    void init();

    /**
     * 获取实例
     * @param key
     * @return
     */
    Object getInstance(String key);
}
