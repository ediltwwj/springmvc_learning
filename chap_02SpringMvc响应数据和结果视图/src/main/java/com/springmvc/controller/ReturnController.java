package com.springmvc.controller;

import com.springmvc.domain.User;
import com.sun.deploy.net.HttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 13967
 * @date 2019/8/14 18:59
 */
@Controller
@RequestMapping("/return")
public class ReturnController {

    @RequestMapping("/string")
    public String testString(Model model){

        System.out.println("执行了testString控制器方法...");
        // 模拟从数据库中查询出User对象
        User user = new User();
        user.setUsername("Coder");
        user.setPassword("123456");
        user.setAge(22);
        // model对象
        model.addAttribute("user1", user);
        return "success";
    }

    @RequestMapping("/void")
    public void testVoid(HttpServletRequest request, HttpServletResponse response) throws Exception{

        System.out.println("执行了testVoid控制器方法...");
        // 不写返回值，默认跳转到void.jsp

        // 编写请求转发程序,转发是一次请求，不用写项目名称,且转发不会执行视图解析器
        // request.getRequestDispatcher("/WEB-INF/pages/success1.jsp").forward(request, response);

        // 重定向，执行新的请求，需要完整路径，尝试好几个都到不了success1.jsp，见鬼了
        // response.sendRedirect(request.getContextPath() + "/index.jsp");

        // 设置中文乱码
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        // 直接进行响应
        response.getWriter().print("也不知道要返回什么内容...");
        return;
    }

    @RequestMapping("/modelandview")
    public ModelAndView testModelAndView(){

        System.out.println("执行了testModelAndView控制器方法...");
        ModelAndView mv = new ModelAndView();

        User user = new User();
        user.setUsername("Coder");
        user.setPassword("123456");
        user.setAge(22);

        // 把user对象存储到mv中，也会把user对象存到request对象中
        mv.addObject("user2", user);
        // 跳转到哪个页面
        mv.setViewName("success1");
        return mv;
    }
}
