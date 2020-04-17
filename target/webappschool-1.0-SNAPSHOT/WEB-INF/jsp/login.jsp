<%--
  Created by IntelliJ IDEA.
  User: bhies
  Date: 25.09.2019
  Time: 09:28
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <link rel="shortcut icon" type="image/jpg">
    <title>Betina & Jessie's school-management application</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

</head>
<body>
<br>
<header>
    <h1>School-Management Application       <img src="/img/school.png" alt="school" height="90"></h1> <br>
</header>

<form:form id="loginForm" modelAttribute="login" action="loginProcess" method="post">
    <table align="center">
        <tr>
            <td>
                <form:label path="username">Username: </form:label>
            </td>
            <td>
                <form:input path="username" name="username" id="username" />
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="password">Password:</form:label>
            </td>
            <td>
                <form:password path="password" name="password" id="password" />
            </td>
        </tr>
        <tr>
            <td></td>
            <td align="left">
                <form:button id="login" name="login">Login</form:button>
            </td>
        </tr>
    </table>
</form:form>

<p style="font-style: italic; color: red;">${message}</p>
<a href="/home">Home</a>
<footer>
    <p>&copy; 2019, Betina Hiestand und Jessie Kobel </p>
</footer>
</body>

</html>