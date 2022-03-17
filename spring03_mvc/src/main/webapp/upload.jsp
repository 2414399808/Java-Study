<%--
  Created by IntelliJ IDEA.
  User: 2414399808
  Date: 2022/2/11
  Time: 19:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/quick21" method="post" enctype="multipart/form-data">
    名称 <input type="text" name="username"><br>
    上传文件<input type="file" name="uploadFile"><br>
    <input type="submit" value="提交">
</form>
</body>
</html>
