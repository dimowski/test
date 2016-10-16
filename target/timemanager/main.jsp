<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Locale" %>
<%@ page import="org.apache.logging.log4j.Logger" %>
<%@ page import="org.apache.logging.log4j.LogManager" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Track time</title>
    <link rel="stylesheet" href="css/custom.css">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script type="text/javascript" src="javascript/timestamp.js"></script>
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
                <div class="navbar-brand">Timemanager</div>
            </div>
            <ul class="nav navbar-nav">
                <li class="active"><a href="main.jsp">Track</a></li>
                <li><a href="manage.jsp">Manage</a></li>
                <li><a href="ActivityServlet">Activity</a></li>
                <li><a href="login.html">Logout</a></li>
            </ul>
        </div>
    </nav>

    <div class="page-header">
        <h3><%= new java.text.SimpleDateFormat("EEEE, dd MMMM y", Locale.ENGLISH).format(new java.util.Date()) %>
        </h3>
    </div>
    <form id="timeForm" method="post" action="MainServlet">
        <div class="col-sm-6 col-sm-offset-3">
            <div class="form-group">
                <label for="category">Category</label>
                <select class="form-control" id="category" name="category">
                    <option></option>
                    <c:forEach var="tempCategory" items="${CATEGORY_LIST}">
                        <option>${tempCategory}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label for="subcategory">Subcategory</label>
                <select class="form-control" id="subcategory" name="subcategory">
                    <option></option>
                    <c:forEach var="tempSubcategory" items="${SUBCATEGORY_LIST}">
                        <option>${tempSubcategory}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label for="startTimeText">Start time</label>
                <div class="row">
                    <div class="col-md-10">
                        <input class="form-control" id="startTimeText" type="text" maxlength="5" size="5"
                               name="startTime"
                               required/>
                    </div>
                    <div class="col-md-2">
                        <button class="btn btn-default" type="button" onclick="displayStartTime()">Now</button>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label for="finishTimeText">Finish time</label>
                <div class="row">
                    <div class="col-md-10">
                        <input class="form-control" id="finishTimeText" type="text" maxlength="5" size="5"
                               name="finishTime"/>
                    </div>
                    <div class="col-md-2">
                        <button class="btn btn-default" type="button" onclick="displayFinishTime()">Now</button>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label for="desc">Description</label>
                <textarea id="desc" class="form-control" rows="2" cols="50" name="desc"></textarea>
            </div>
            <div>
                <button type="submit" class="btn btn-primary">Submit</button>
            </div>
        </div>
    </form>
</div>
</body>
</html>