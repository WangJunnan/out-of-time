package com.taro.oot.core.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taro.core.factory.ParamResolveFactory;
import com.taro.core.help.PathAndBeanAnalyser;
import com.taro.core.help.PathAndBeanMapper;
import com.taro.core.registry.ParamResolveRegistry;
import com.taro.core.request.HttpContext;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.velocity.VelocityViewResolver;
import org.springframework.web.util.UrlPathHelper;

/**
 * @author wangjn
 * @description
 * @date Created on 2018/3/14.
 */
@Controller("jsonController")
public class JsonController implements ApplicationContextAware, InitializingBean{

    private UrlPathHelper urlPathHelper = new UrlPathHelper();

    protected ApplicationContext applicationContext;

    private PathAndBeanAnalyser pathAndBeanAnalyser;

    @Autowired
    private ParamResolveRegistry paramResolveRegistry;

    @Autowired
    private VelocityViewResolver velocityViewResolver;


    @RequestMapping(value = { "/**/*.json" })
    public ModelAndView doAction(HttpServletRequest request, HttpServletResponse response)
        throws InvocationTargetException, IllegalAccessException {
        String url = urlPathHelper.getLookupPathForRequest(request);
        PathAndBeanMapper pathAndBeanMapper = pathAndBeanAnalyser.analyse(url);
        Object bean = pathAndBeanMapper.getBean();
        Method method = pathAndBeanMapper.getMethod();
        // http 上下文
        HttpContext context = new HttpContext(request, response);
        Object result = method.invoke(bean, new ParamResolveFactory(paramResolveRegistry, context, method).getMethodParameters());

        ModelAndView view = new ModelAndView("view");
        view.setViewName(pathAndBeanMapper.getViewName());
        view.addObject("result", result);
        try {
            if (velocityViewResolver.resolveViewName(pathAndBeanMapper.getViewName(), Locale.CHINA) != null)
            response.setHeader("Content-type", "application/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");

            PrintWriter out = null;
            try {
                out = response.getWriter();
            } catch (IOException e) {
                e.printStackTrace();
            }
            out.print(JSON.toJSONString(result));
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.pathAndBeanAnalyser = new PathAndBeanAnalyser(applicationContext);
    }
}
