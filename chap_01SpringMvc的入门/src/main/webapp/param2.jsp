<%--
  Created by IntelliJ IDEA.
  User: 13967
  Date: 2019/8/13
  Time: 10:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h3>请求参数绑定单个实体类型</h3>
    <form action="param2" method="post">
        UserName:<input type="text" name="username" /><br/>
        Password:<input type="text" name="password" /><br/>
        Money:<input type="text" name="money" /><br/>
        <input type="submit" value="提交"/>
    </form>
</body>
</html>
