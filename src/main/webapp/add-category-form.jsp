<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add category</title>
    <link rel="stylesheet" type="text/css" href="css/custom.css">
</head>
<body>
    <header></header>
    <nav>
        <ul>
            <li class="selected"><a href="main.jsp">Track</a></li>
            <li><a href="manage.jsp">Manage</a></li>
            <li><a href="">com.timemanager.model.Activity</a></li>
        </ul>
    </nav>
        <h3>Add category</h3>
        <br/>
        <form action="MainServlet" method="get">
            <input type="hidden" name="command" value="ADD_CATEGORY"/>
            <table>
                <tbody>
                    <tr>
                        <td><label>Category</label></td>
                        <td><input type="text" name="category"></td>
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
