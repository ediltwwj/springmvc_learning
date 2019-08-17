package com.springmvc.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 13967
 * @date 2019/8/17 14:05
 * 自定义拦截器
 */
// 接口中的三个方法都已经默认实现了
public class MyInterceptor1 implements HandlerInterceptor {

    /**
     * 预处理，controller方法执行前执行
     * return true，放行，执行下一个拦截器，直到没有，再执行controller中的方法
     * return false，不放行
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println("MyInterceptor1执行了预处理...控制器前");
        return true;
    }

    /**
     * 后处理,controller方法执行前执行,success.jsp执行前
     * 如果跳转了页面则不会跳转到success方法，上面同理
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        System.out.println("MyInterceptor1执行了后处理...控制器后");
    }

    /**
     * success.jsp页面执行后，该方法才会执行
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        System.out.println("MyInterceptor1执行了最后处理...跳转页面之后");
    }
}
