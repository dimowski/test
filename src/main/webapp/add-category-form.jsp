<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add category</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
</head>

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
                <li><a href="MainServlet?command=activity">Activity</a></li>
                <li><a href="login.html">Logout</a></li>
            </ul>
        </div>
    </nav>

    <div class="page-header">
        <h3>Add category</h3>
    </div>

    <form action="MainServlet">
        <input type="hidden" name="command" value="ADD_CATEGORY"/>
        <div class="col-md-4">
            <div class="form-group">
                <label for="category">Category</label>
                <input class="form-control" id="category" type="text" name="category" size="50">
            </div>
            <div>
                <input class="btn btn-primary" type="submit" value="Save"/>
                <a class="btn btn-default" href="manage.jsp">Back</a>
            </div>
        </div>
    </form>
</div>
</body>
</html>
