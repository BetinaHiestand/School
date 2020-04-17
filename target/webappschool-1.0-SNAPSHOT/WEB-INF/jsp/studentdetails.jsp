<%@ page import="com.ubs.school.domain.Login" %><%--
  Created by IntelliJ IDEA.
  User: jessie
  Date: 16.10.2019
  Time: 09:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    <h1>Student view <a href="/index"><img src="/img/school.png" alt="school" height="90"></a></h1> <br>
</header>
<nav>
    <ul>

        <li><a href="classes">Classes</a></li>
        <li><a href="students">Students</a></li>
        <li><a href="teachers">Teachers</a></li>

    </ul>
</nav>
<br>
<h2>
    ${studentDetails[0].student.firstName} ${studentDetails[0].student.lastName} ${studentDetails[0].student.clazz.className}<br> <br>
</h2>

<table>
    <tr>
        <th>Subject</th>
        <th>Grades</th>
        <th>Average</th>
        <th>Passed</th>
    </tr>

    <c:forEach items="${studentDetails}" var="studentDetails" varStatus="loopCount">
        <tr>
            <td>
                    ${studentDetails.subject.subjectName}
            </td>
            <td>
                <c:forEach items="${studentDetails.grades}" var="grades" varStatus="loopCount">
                    <c:choose>
                        <c:when test="${loopCount.first}">
                            ${grades}
                        </c:when>
                        <c:when test="${loopCount.last}">
                            , ${grades}
                        </c:when>
                        <c:otherwise>
                            , ${grades}
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </td>
            <td>
                    ${studentDetails.average}
            </td>
            <td>
                <c:choose>
                    <c:when test="${studentDetails.average gt 3.9}">
                        <img src="/img/tick.png" alt="picture green tick" height="20">
                    </c:when>
                    <c:otherwise>
                        <img src="/img/cross.png" alt="picture red cross" height="20">
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
</table>

<a href="/index">Home</a>
<br>
<br>
<footer>
    <p>&copy; 2019, Betina Hiestand und Jessie Kobel
</footer>
</body>
</html>