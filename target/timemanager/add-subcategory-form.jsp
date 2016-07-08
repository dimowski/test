<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add subcategory</title>
    <link rel="stylesheet" type="text/css" href="css/main-page.css">
</head>
<body>
<header></header>
<nav>
    <ul>
        <li class="selected"><a href="main.jsp">Track</a></li>
        <li><a href="manage.jsp">Manage</a></li>
        <li><a href="">Activity</a></li>
    </ul>
</nav>
<h3>Add subcategory</h3>
<br/>
<form action="MainServlet" method="get">
    <input type="hidden" name="command" value="ADD_SUBCATEGORY"/>
    <table>
        <tbody>
        <tr>
            <td><label>Subcategory</label></td>
            <td><input type="text" name="subcategory"></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" value="Save"/></td>
        </tr>
        </tbody>
    </table>
</form>
<hr/>
<p>
    <a href="manage.jsp">Back</a>
</p>
</body>
</html>
