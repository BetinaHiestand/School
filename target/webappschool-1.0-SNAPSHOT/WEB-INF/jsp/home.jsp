<%--
  Created by IntelliJ IDEA.
  User: bhies
  Date: 25.09.2019
  Time: 09:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <meta charset="utf-8">
    <link rel="shortcut icon" href="/img/school.png" type="image/jpg">
    <title>Betina & Jessie's school-management application</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

</head>
<body>
<br>
<header>
    <h1>School-Management Application      <img src="/img/school.png" alt="school" height="90"></h1> <br>
</header>

<table align="center">
    <tr>
        <td><a href="login">Login</a>
        </td>
        <td><a href="register">Register</a>
        </td>
    </tr>
</table>
<p style="font-style: italic; color: red;">${message}</p>

</body>
</html>
