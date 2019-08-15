package com.springmvc.controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.UUID;

/**
 * @author 13967
 * @date 2019/8/15 15:28
 */
@Controller
@RequestMapping("/file")
public class FileUploadController {

    @RequestMapping("/upload1")
    public String fileUpload1(HttpServletRequest request) throws Exception{

        System.out.println("执行fileUpload1控制器方法...");

        // 使用fileupload组件完成文件上传
        // 上传的位置
        String path = request.getSession().getServletContext().getRealPath("/uploads/");
        // 本机在Tomcat目录下
        System.out.println("PATH:" + path);
        // 判断该路径是否存在
        File file = new File(path);
        if(!file.exists()){
            // 创建文件夹
            file.mkdirs();
        }

        // 解析request对象，获取到上传文件项
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        // 解析request
        List<FileItem> items = upload.parseRequest(request);
        // 遍历
        for(FileItem item : items){
            // 进行判断，当前item对象是否是一个上传文件项
            if(item.isFormField()){
                // 普通表单项
            }else {
                // 上传文件项
                // 获取到上传文件的名称
                String filename = item.getName();
                // 把文件名称设置为唯一值,uuid
                String uuid = UUID.randomUUID().toString().replace("-","");
                filename = uuid + "_" + filename;
                // 完成文件上传
                item.write(new File(path, filename));
                // 删除临时文件
                item.delete();
            }
        }
        return "success";
    }
}
