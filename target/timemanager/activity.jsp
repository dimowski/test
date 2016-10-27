<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.apache.logging.log4j.Logger" %>
<%@ page import="org.apache.logging.log4j.LogManager" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Activity</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
</head>

<!-- Проверка авторизации пользователя -->
<%! static Logger log = LogManager.getLogger("manage.jsp"); %>
<%
    String userName = null;
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("userID")) {
                log.info("Cookie found");
                userName = cookie.getValue();
            }
        }
    }
    if (userName == null)
        response.sendRedirect("login.html");
%>

<body>
<div class="container">
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="navbar-header">
                <div class="navbar-brand"><img src="images/logo.png" style="max-width: 100px; height: auto;"></div>
            </div>
            <ul class="nav navbar-nav">
                <li><a href="main.jsp">Track</a></li>
                <li><a href="manage.jsp">Manage</a></li>
                <li class="active"><a href="MainServlet?command=activity">Activity</a></li>
                <li><a href="login.html">Logout</a></li>
            </ul>
        </div>
    </nav>

    <div class="page-header">
        <h3>Activity</h3>
    </div>

    <div>

    </div>

    <div class="table-responsive">
        <table class="table table-hover table-bordered">
            <thead>
            <tr>
                <th>Day</th>
                <th>Category</th>
                <th>Subcategory</th>
                <th>Time</th>
                <th>Duration</th>
                <th>Description</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="tempContact" items="${CONTACT_LIST.values()}">
                <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
