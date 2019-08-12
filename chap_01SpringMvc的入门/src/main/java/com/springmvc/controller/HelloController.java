package com.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 13967
 * @date 2019/8/12 16:54
 * 控制器类
 */
@Controller
@RequestMapping(path="/springmvc") // 相当于一级路径
public class HelloController {

    /**
     * @RequestMapping(path="/requestMapping")
     * @RequestMapping(value="/requestMapping")
     * @RequestMapping("/requestMapping")
     * 上面三种等价
     * @return
     */
    @RequestMapping(path="/requestMapping")
    public String testRequestMapping(){
        System.out.println("测试RequestMapping注解...");
        return "success";
    }
}
