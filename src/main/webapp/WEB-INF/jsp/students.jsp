<%@ page import="com.ubs.school.domain.Login" %><%--
  Created by IntelliJ IDEA.
  User: bhies
  Date: 09.05.2019
  Time: 09:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
    <h1>Students <a href="/index"><img src="/img/school.png" alt="school" height="90"></a></h1> <br>
</header>
<nav>
    <ul>

        <li><a href="classes">Classes</a></li>
        <li><a href="students">Students</a></li>
        <li><a href="teachers">Teachers</a></li>

    </ul>
</nav>
<br>
<h2>Search a Student</h2>
<form action="searchStudent" method="POST">
    First Name: <input type="text" name="firstName">
    <br/>
    Last Name: <input type="text" name="lastName"/> <br/>
    <input type="submit" value="Submit"/>
</form>

<c:if test="${fn:length(students) > 0}">
    <h2>Search Result</h2>
    <table>
        <tr>
            <td>First name</td>
            <td>Last name</td>
            <td>Birthday</td>
            <td>Schoolclass</td>
        </tr>

        <c:forEach items="${students}" var="student">
            <tr>
                <td><a href="/studentdetails?studentId=${student.id}">${student.firstName}</a></td>
                <td>${student.lastName}</td>
                <td>${student.birthDate}</td>
                <td>${student.clazz.className}</td>
            </tr>
        </c:forEach>
    </table>

</c:if>
<br>
<a href="studentform">Add a new student</a>
<br>
<a href="/index">Home</a>
<br>
<p> Your current session: <%
    out.println(request.getSession().getId());
%></p>
<br>
<footer>
    <p>&copy; 2019, Betina Hiestand und Jessie Kobel
</footer>
</body>
</html>