<%--
  Created by IntelliJ IDEA.
  User: 周都
  Date: 2021/2/9
  Time: 14:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>设置新密码</title>
</head>
<body>
    <h1>设置新密码</h1>
    <form action="/resetPassword" method="post">
        <input type="hidden" name="p" value="${p}">
        新密码: <input type="text" name="password">
        <input type="submit" value="提交">
    </form>
</body>
</html>
