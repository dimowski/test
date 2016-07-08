<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Update category</title>
    <link rel="stylesheet" type="text/css" href="css/main-page.css">
</head>
<body>
<nav>
    <ul>
        <li><a href="main.jsp">Track</a></li>
        <li class="selected"><a href="manage.jsp">Manage</a></li>
        <li><a href="">Activity</a></li>
    </ul>
</nav>

<div>
    <h3>Update category</h3>

    <form action="MainServlet" method="GET">

        <input type="hidden" name="command" value="UPDATE_CATEGORY" />
        <input type="hidden" name="categoryName" value="${param.get("categoryName")}"/>

        <table>
            <tbody>
            <tr>
                <td><label>Category:</label></td>
                <td><input type="text" name="newCategory"
                           value="${param.get("categoryName")}"/></td>
            </tr>
            <tr>
                <td></td>
                <td><input type="submit" value="Update" class="save"/></td>
            </tr>
            </tbody>
        </table>
    </form>

    <p>
        <a href="manage.jsp">Back</a>
    </p>

</div>
</body>
</html>
