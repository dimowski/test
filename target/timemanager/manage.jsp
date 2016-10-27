<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.apache.logging.log4j.LogManager" %>
<%@ page import="org.apache.logging.log4j.Logger" %>
<html>
<head>
    <title>Manage</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
</head>

<!-- Проверка авторизации пользователя -->
<%! static Logger log = LogManager.getLogger("manage.jsp"); %>
<%
    String userId = null;
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("userID")) {
                log.info("Cookie found");
                userId = cookie.getValue();
            }
        }
    }
    if (userId == null)
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
                <li class="active"><a href="manage.jsp">Manage</a></li>
                <li><a href="MainServlet?command=activity">Activity</a></li>
                <li><a href="login.html">Logout</a></li>
            </ul>
        </div>
    </nav>

    <div class="row">
        <div class="col-md-6">
            <div class="page-header">
                <h3 class="text-center">Categories</h3>
            </div>

            <c:if test="${CATEGORY_LIST==null}">
                <div class="alert alert-info">No categories</div>
                <button class="btn btn-primary" onclick="window.location.href='add-category-form.jsp'">
                    Add category
                </button>
            </c:if>
            <c:if test="${CATEGORY_LIST!=null}">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <div class="text-right">
                            <button class="btn btn-primary" onclick="window.location.href='add-category-form.jsp'">
                                Add category
                            </button>
                        </div>
                    </div>
                    <table class="table table-hover table-bordered">
                        <thead>
                        <tr>
                            <th class="text-center">Category</th>
                            <th class="text-center">Action</th>
                        </tr>
                        </thead>
                        <c:forEach var="tempCategory" items="${CATEGORY_LIST}">

                            <c:url var="tempLink" value="update-category-form.jsp">
                                <c:param name="command" value="UPDATE_CATEGORY"/>
                                <c:param name="name" value="${tempCategory.name}"/>
                            </c:url>
                            <c:url var="deleteLink" value="MainServlet">
                                <c:param name="command" value="DELETE_CATEGORY"/>
                                <c:param name="name" value="${tempCategory.name}"/>
                            </c:url>

                            <tr>
                                <td>${tempCategory.name}</td>
                                <td class="text-center"><a href="${tempLink}">Update</a>
                                    |<a href="${deleteLink}"
                                        onclick="if (!(confirm('Are you sure?'))) return false;">Delete</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </c:if>
        </div>

        <div class="col-md-6">
            <div class="page-header">
                <h3 class="text-center">Subcategories</h3>
            </div>
            <c:if test="${SUBCATEGORY_LIST==null}">
                <div class="alert alert-info">No subcategories</div>
                <button class="btn btn-primary" onclick="window.location.href='add-subcategory-form.jsp'">
                    Add subcategory
                </button>
            </c:if>
            <c:if test="${SUBCATEGORY_LIST!=null}">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <div class="text-right">
                            <button class="btn btn-primary" onclick="window.location.href='add-subcategory-form.jsp'">
                                Add subcategory
                            </button>
                        </div>
                    </div>
                    <table class="table table-hover table-bordered">
                        <thead>
                        <tr>
                            <th class="text-center">Subcategory</th>
                            <th class="text-center">Action</th>
                        </tr>
                        </thead>
                        <c:forEach var="tempSubcategory" items="${SUBCATEGORY_LIST}">

                            <c:url var="tempLink" value="update-subcategory-form.jsp">
                                <c:param name="command" value="UPDATE_SUBCATEGORY"/>
                                <c:param name="name" value="${tempSubcategory.name}"/>
                            </c:url>
                            <c:url var="deleteLink" value="MainServlet">
                                <c:param name="command" value="DELETE_SUBCATEGORY"/>
                                <c:param name="name" value="${tempSubcategory.name}"/>
                            </c:url>
                            <tr>
                                <td>${tempSubcategory.name}</td>
                                <td class="text-center"><a href="${tempLink}">Update</a>
                                    |<a href="${deleteLink}"
                                        onclick="if (!(confirm('Are you sure?'))) return false;">Delete</a></td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </c:if>
        </div>
    </div>

</div>
</body>
</html>
