<%--
  Created by IntelliJ IDEA.
  User: 2414399808
  Date: 2022/2/11
  Time: 15:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/quick13" method="post">
    <input type="text" name="userList[0].username">
    <input type="text" name="userList[0].age">
    <input type="text" name="userList[1].username">
    <input type="text" name="userList[1].age">
    <input type="text" name="userList[2].username">
    <input type="text" name="userList[2].age">
    <input type="submit" value="提交">
</form>
</body>
</html>
