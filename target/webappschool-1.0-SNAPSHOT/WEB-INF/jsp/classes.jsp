<%@ page import="com.ubs.school.domain.Login" %><%--
  Created by IntelliJ IDEA.
  User: jessie
  Date: 16.10.2019
  Time: 09:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    if (session.getAttribute("login") == null) {
        response.sendRedirect("/login");
    }
    else {
        Login login = (Login) session.getAttribute("login");
        if (login.getUsername() == null){
            response.sendRedirect("/login");
        }
    }
%>
<html>
<head>
    <meta charset="utf-8">
    <link rel="shortcut icon" href="/img/school.png" type="image/jpg">
    <title>Betina & Jessie's school-management application</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

</head>
<body>
<form action="logout" method="POST">
    Current User:
    <%
        Login login = (Login) session.getAttribute("login");
        out.println(login.getUsername());
    %>
    <input type="submit" value="Logout"/>
</form>
<header>
    <h1>Classes <a href="/index"><img src="/img/school.png" alt="school" height="90"></a></h1> <br>
</header>
<nav>
    <ul>

        <li><a href="classes">Classes</a></li>
        <li><a href="students">Students</a></li>
        <li><a href="teachers">Teachers</a></li>

    </ul>
</nav>
<br>
<% if (request.getParameter("error") != null) {
    if (request.getParameter("error").equals("0")) { %>
<p> Class successfully added</p>
<%} else if (request.getParameter("error").equals("1")) {%>
<p> An error occured, please try again </p>
<%
        }
    }
%>

<table>
    <tr>
        <th>Class Name</th>
        <th>First Name Teacher</th>
        <th>Last Name Teacher</th>
    </tr>
    <c:forEach items="${classList}" var="schoolClass">
        <tr>
            <td><a href="/studentsOfaClass?className=${schoolClass.className}">${schoolClass.className}</a></td>
            <td>${schoolClass.teacher.firstName}</td>
            <td>${schoolClass.teacher.lastName}</td>
        </tr>
    </c:forEach>
</table>
<br>
<a href="/classform">Add school class</a>
<br>
<p> Your current session: <%
    out.println(request.getSession().getId());
%></p>
<a href="/index">Home</a>
<br>
<br>
<footer>
    <p>&copy; 2019, Betina Hiestand und Jessie Kobel
</footer>
</body>
</html>