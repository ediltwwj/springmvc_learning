package com.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 13967
 * @date 2019/8/17 13:58
 */
@Controller
@RequestMapping("/interceptor")
public class InterceptorController {

    @RequestMapping("/do1")
    public String testInterceptor(){

        System.out.println("执行了testInterceptor控制器方法...");

        return "success";
    }
}
