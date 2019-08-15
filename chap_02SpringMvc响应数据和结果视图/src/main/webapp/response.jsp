<%--
  Created by IntelliJ IDEA.
  User: 13967
  Date: 2019/8/14
  Time: 18:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="js/jquery-3.1.1.min.js"></script>
    <script>
        // 页面加载，绑定单击事件
        $(function () {
            $("#btn").click(function () {
                // 发送ajax请求
                $.ajax({
                    // 编写json格式，设置属性和值
                    url: "return/json",
                    contentType: "application/json;charset=UTF-8",
                    data: '{"username":"MaGrady", "password":"123456", "age":18}', // 发送给服务器的数据
                    dataType: "json",
                    type: "post",

                    success:function (data) {
                        // data是服务器响应的json数据，进行解析
                        alert(data);
                        alert(data.password);
                    }
                });
            });
        });
    </script>
</head>
<body>
    <a href="/return/string">响应值之返回类型是String类型...</a>

    <hr/>

    <a href="/return/void">响应值之返回类型是Void类型...</a>

    <hr/>

    <a href="/return/modelandview">响应值之返回值是ModelAndView类型...</a>

    <hr/>

    <a href="/return/forwardOrRedirect">响应之使用转发或者重定向...</a>

    <hr/>

    <button id="btn">发送ajax请求...</button>
</body>
</html>
