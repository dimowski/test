<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.timemanager.MainServlet" %>
<%@ page import="java.util.Locale" %>
<%@ page import="org.apache.logging.log4j.Logger" %>
<%@ page import="org.apache.logging.log4j.LogManager" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Track time</title>
    <link rel="stylesheet" type="text/css" href="css/main-page.css">
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
<header></header>
<nav>
    <ul>
        <li class="selected"><a href="main.jsp">Track</a></li>
        <li><a href="manage.jsp">Manage</a></li>
        <li><a href="ActivityServlet">Activity</a></li>
    </ul>
</nav>

<h1 align="center"><%= new java.text.SimpleDateFormat("EEEE, dd MMMM y", Locale.ENGLISH).format(new java.util.Date()) %>
</h1>
<hr/>
<form id="timeForm" method="post" action="MainServlet">
    <div align="center">
        <table>
            <tbody>
                <tr>
                    <td><label>Category</label></td>
                    <td>
                        <select name="category">
                        <option></option>
                        <c:forEach var="tempCategory" items="${CATEGORY_LIST}">
                            <option>${tempCategory}</option> -->
                        </c:forEach>
                        </select>
                    </td>
                    <td><label>Subcategory</label></td>
                    <td><select name="subcategory">
                        <option></option>
                        <c:forEach var="tempSubcategory" items="${SUBCATEGORY_LIST}">
                            <option>${tempSubcategory}</option>
                        </c:forEach>
                    </select></td>
                </tr>
                <tr>
                    <td><label>Start time</label></td>
                    <td>
                        <input id="startTimeText" type="text" maxlength="5" size="5" name="startTime" value="" required/>
                        <button class="button" type="button" onclick="displayStartTime()">Now</button>
                    </td>
                    <td><label>Finish time</label></td>
                    <td>
                        <input id="finishTimeText" type="text" maxlength="5" size="5" name="finishTime"/>
                        <button class="button" type="button" onclick="displayFinishTime()">Now</button>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>

    <!--
    <div align="center">
        <label>Start time</label>
        <input id="startTimeText" type="text" maxlength="5" size="5" name="startTime" value="" required/>
        <button class="button" type="button" onclick="displayStartTime()">Now</button>

        <label>Finish time</label>
        <input id="finishTimeText" type="text" maxlength="5" size="5" name="finishTime"/>
        <button class="button" type="button" onclick="displayFinishTime()">Now</button>
    </div>

    <div>
        Duration <input type="text" disabled="disabled" value="21:05"/>
    </div>
-->
    <div align="center">
        Description <textarea rows="2" cols="50" name="desc"></textarea>
    </div>

    <div align="center"><input type="submit" value="Submit" class="button"/></div>
    <hr/>
</form>
</body>
</html>