<%--
  Created by IntelliJ IDEA.
  User: 13967
  Date: 2019/8/14
  Time: 13:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <!-- 测试RequestBody注解 -->
    <form action="requestBody" method="post">
        姓名:<input type="text" name="username"/><br/>
        年龄:<input type="text" name="age"/><br/>
        <input type="submit" value="提交"/>
    </form>
</body>
</html>
