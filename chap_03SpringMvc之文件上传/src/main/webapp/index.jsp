<%--
  Created by IntelliJ IDEA.
  User: 13967
  Date: 2019/8/15
  Time: 15:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h2>传统方式上传文件</h2>

    <form action="/file/upload1" method="post" enctype="multipart/form-data">
        选择文件: <input type="file" name="upload1"/><br/>
        <input type="submit" value="上传"/>
    </form>

    <hr/>

    <h2>springmvc方式上传文件</h2>

    <form action="/file/upload2" method="post" enctype="multipart/form-data">
        选择文件: <input type="file" name="upload2"/><br/>
        <input type="submit" value="上传"/>
    </form>
</body>
</html>
