<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.timemanager.MainServlet" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="css/main-page.css">
</head>

<!-- Проверка авторизации пользователя -->
<%
    String userName = null;
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("userID")) {
                MainServlet.log.info("Cookie found");
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
        <li><a href="main.jsp">Track</a></li>
        <li class="selected"><a href="manage.jsp">Manage</a></li>
        <li><a href="">Activity</a></li>
    </ul>
</nav>

    <div align="center">
        <h3>Categories</h3>
    </div>

    <div align="center">
        <c:if test="${CATEGORY_LIST==null}">
            No categories
        </c:if>
        <c:if test="${CATEGORY_LIST!=null}">
            <table>
                <tr>
                    <th>Category</th>
                    <th>Action</th>
                </tr>
                <c:forEach var="tempCategory" items="${CATEGORY_LIST}">

                    <c:url var="tempLink" value="update-category-form.jsp">
                        <c:param name="command" value="UPDATE_CATEGORY" />
                        <c:param name="categoryName" value="${tempCategory}" />
                    </c:url>

                    <c:url var="deleteLink" value="MainServlet">
                        <c:param name="command" value="DELETE_CATEGORY" />
                        <c:param name="categoryName" value="${tempCategory}" />
                    </c:url>

                    <tr>
                        <td>${tempCategory}</td>
                        <td><a href="${tempLink}">Update</a>
                            |<a href="${deleteLink}" onclick="if (!(confirm('Are you sure?'))) return false;">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </div>
    <div align="center">
    <input type="button" value="Add category" align="center"
           onclick="window.location.href='add-category-form.jsp'; return false;"
    />
    </div>
    <hr/>
    <div align="center">
        <h3>Subcategories</h3>
    </div>
    <div align="center">
        <c:if test="${SUBCATEGORY_LIST==null}">
            No subcategories
        </c:if>
        <c:if test="${SUBCATEGORY_LIST!=null}">
        <table>
            <tr>
                <th>Subcategory</th>
                <th>Action</th>
            </tr>
            <c:forEach var="tempSubcategory" items="${SUBCATEGORY_LIST}">

                <c:url var="tempLink" value="update-subcategory-form.jsp">
                    <c:param name="command" value="UPDATE_SUBCATEGORY" />
                    <c:param name="subcategoryName" value="${tempSubcategory}" />
                </c:url>

                <c:url var="deleteLink" value="MainServlet">
                    <c:param name="command" value="DELETE_SUBCATEGORY" />
                    <c:param name="subcategoryName" value="${tempSubcategory}" />
                </c:url>

                <tr>
                    <td>${tempSubcategory}</td>
                    <td><a href="${tempLink}">Update</a>
                        |<a href="${deleteLink}" onclick="if (!(confirm('Are you sure?'))) return false;">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
        </c:if>
    </div>
    <div align="center">
    <input type="button" value="Add subcategory"
           onclick="window.location.href='add-subcategory-form.jsp'; return false;"
    />
    </div>
</body>
</html>
