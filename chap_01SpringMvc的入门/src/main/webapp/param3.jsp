<%--
  Created by IntelliJ IDEA.
  User: 13967
  Date: 2019/8/13
  Time: 11:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h3>请求参数绑定实体类型中含有实体类型</h3>
    <form action="param3" method="post">
        UserName:<input type="text" name="account.username" /><br/>
        Password:<input type="text" name="account.password" /><br/>
        Money:<input type="text" name="account.money" /><br/>
        EmployeeName:<input type="text" name="employeeName"><br/>
        <input type="submit" value="提交"/>
    </form>
</body>
</html>
