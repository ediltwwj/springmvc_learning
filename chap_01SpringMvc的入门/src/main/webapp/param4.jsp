<%--
  Created by IntelliJ IDEA.
  User: 13967
  Date: 2019/8/13
  Time: 11:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h3>请求参数绑定集合类型</h3>
    <form action="param4" method="post">
        BankName:<input type="text" name="bankName"/><br/><hr/>
        <!-- list -->
        UserName1L:<input type="text" name="list[0].username"/><br/>
        Password1L:<input type="text" name="list[0].password"/><br/>
        Money1L:<input type="text" name="list[0].money"/><br/>
        <hr/>
        UserName2L:<input type="text" name="list[1].username"/><br/>
        Password2L:<input type="text" name="list[1].password"/><br/>
        Money2L:<input type="text" name="list[1].money"/><br/>
        <hr/>
        <!-- Map -->
        UserName1M:<input type="text" name="map[0].username"/><br/>
        Password1M:<input type="text" name="map[0].password"/><br/>
        Money1M:<input type="text" name="map[0].money"/><br/>
        <hr/>
        UserName2M:<input type="text" name="map['1'].username"/><br/>
        Password2M:<input type="text" name="map['1'].password"/><br/>
        Money2M:<input type="text" name="map['1'].money"/><br/>
        <input type="submit" value="提交"/>
    </form>
</body>
</html>
