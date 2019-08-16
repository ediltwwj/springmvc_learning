package com.springmvc.controller;

import com.springmvc.exception.SysException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 13967
 * @date 2019/8/16 21:30
 */
@Controller
@RequestMapping("/exception")
public class ExceptionController {

    @RequestMapping("/deal")
    public String testException() throws SysException{

        System.out.println("执行了testException控制器方法...");

        try {
            // 模拟异常
            int a = 10/0;
        } catch (Exception e) {
            // 打印异常信息
            e.printStackTrace();
            // 抛出自定义异常信息
            throw new SysException("模拟异常信息提示...");
        }

        return "success";
    }
}
