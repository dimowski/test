<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Update subcategory</title>
    <link rel="stylesheet" type="text/css" href="css/custom.css">
</head>
<body>
<nav>
    <ul>
        <li><a href="main.jsp">Track</a></li>
        <li class="selected"><a href="manage.jsp">Manage</a></li>
        <li><a href="">com.timemanager.model.Activity</a></li>
    </ul>
</nav>

<div>
    <h3>Update subcategory</h3>

    <form action="MainServlet" method="GET">

        <input type="hidden" name="command" value="UPDATE_SUBCATEGORY" />
        <input type="hidden" name="subcategoryName" value="${param.get("subcategoryName")}"/>

        <table>
            <tbody>
            <tr>
                <td><label>Subcategory:</label></td>
                <td><input type="text" name="newSubcategory"
                           value="${param.get("subcategoryName")}"/></td>
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
