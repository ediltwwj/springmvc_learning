package com.springmvc.controller;

import com.springmvc.domain.Account;
import com.springmvc.domain.Bank;
import com.springmvc.domain.Employee;
import com.springmvc.domain.Student;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 13967
 * @date 2019/8/13 10:26
 * 请求参数绑定
 */
@Controller
public class ParamController {

    /**
     * 请求参数绑定基本类型
     * @return
     */
    @RequestMapping("/param1")
    public String testParam1(String username, String password){

        System.out.println("UserName: " + username + ", Password: " + password);
        return "success";
    }

    /**
     * 请求参数绑定单个实体类型
     * @return
     */
    @RequestMapping("/param2")
    public String testParam2(Account account){

        System.out.println(account);
        return "success";
    }

    /**
     * 请求参数绑定实体类型中含有实体类型
     * @param employee
     * @return
     */
    @RequestMapping("/param3")
    public String testParam3(Employee employee){

        System.out.println(employee);
        return "success";
    }

    /**
     * 请求参数绑定集合类型
     * @param bank
     * @return
     */
    @RequestMapping("/param4")
    public String testParam4(Bank bank){

        System.out.println(bank);
        return "success";
    }

    /**
     * 自定义类型转换器
     * @param student
     * @return
     */
    @RequestMapping("/param5")
    public String testParam5(Student student){

        System.out.println(student);
        return "success";
    }
}
