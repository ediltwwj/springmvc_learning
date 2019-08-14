package com.springmvc.controller;

import com.springmvc.domain.Account;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author 13967
 * @date 2019/8/14 13:16
 * 常用的注解
 */
@Controller
@SessionAttributes(value = {"msg"})  // 把msg=该存啥呢存入到session域对中
public class AnnoController {

    @RequestMapping("/requestParam")
    public String testRequestParam(@RequestParam(value = "name", required = false) String username){

        System.out.println("Name:" + username);
        return "success";
    }

    @RequestMapping("/requestBody")
    public String testRequestBody(@RequestBody String body){

        System.out.println("Body: " + body);
        return "success";
    }

    @RequestMapping("/pathVariable/{sid}")
    public String testPathVariable(@PathVariable(value = "sid") String id){

        System.out.println("ID: " + id);
        return "success";
    }

    /*
    @RequestMapping("/modelAttribute1")
    public String testModelAttribute(Account account){

        System.out.println("ModelAttribute控制器方法执行...");
        System.out.println(account);
        return "success";
    }



    @ModelAttribute
    public Account showAccount(String username, String password){
        System.out.println("有返回值...");
        // 模拟查询数据库，设置money，返回Account对象给控制器
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        account.setMoney(200.0);

        return account;
    }
    */

    /*
    @RequestMapping("/modelAttribute2")
    public String testModelAttribute(@ModelAttribute("1") Account account){

        System.out.println("ModelAttribute控制器方法执行...");
        System.out.println(account);
        return "success";
    }

    @ModelAttribute
    public void showAccount(String username, String password, Map<String, Account> map){
        System.out.println("无返回值...");
        // 模拟查询数据库，设置money,将Account放进Map
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        account.setMoney(200.0);
        map.put("1", account);
    }
    */

    @RequestMapping("/sessionAttributes")
    public String testSessionAttributes(Model model){
        System.out.println("SessionAttributes...");
        // 底层会存到request域对象中
        model.addAttribute("msg","存啥好呢？");
        return "success";
    }

}
