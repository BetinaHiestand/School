<%--
  Created by IntelliJ IDEA.
  User: bhies
  Date: 25.09.2019
  Time: 09:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
    <h1>School-Management Application       <img src="/img/school.png" alt="school" height="90"></h1> <br>
</header>

<form:form id="regForm" modelAttribute="user" action="registerProcess" method="post">
    <table align="center">
        <tr>
            <td>
                <form:label path="username">Username</form:label>
            </td>
            <td>
                <form:input path="username" name="username" id="username" />
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="password">Password</form:label>
            </td>
            <td>
                <form:password path="password" name="password" id="password" />
            </td>
        </tr>
        <tr>
            <td>
                <form:button id="register" name="register">Register</form:button>
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
