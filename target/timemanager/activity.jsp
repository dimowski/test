<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.apache.logging.log4j.Logger" %>
<%@ page import="org.apache.logging.log4j.LogManager" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Activity</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="javascript/jquery.min.js"></script>
    <script src="javascript/highcharts.js"></script>
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
            <c:forEach var="tempActivity" items="${ACTIVITY}">
                <tr>
                    <td>${tempActivity.date}</td>
                    <td>${tempActivity.category}</td>
                    <td>${tempActivity.subcategory}</td>
                    <td>${tempActivity.start} - ${tempActivity.finish}</td>
                    <td>${tempActivity.duration}</td>
                    <td>${tempActivity.description}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="row">
        <div class="col-md-6">
            <div id="container" style="width: 550px; height: 400px; margin: 0 auto"></div>
            <script language="JavaScript">
                $(document).ready(function () {
                    var chart = {
                        plotBackgroundColor: null,
                        plotBorderWidth: null,
                        plotShadow: false
                    };
                    var title = {
                        text: 'Categories'
                    };
                    var tooltip = {
                        pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
                    };
                    var plotOptions = {
                        pie: {
                            allowPointSelect: true,
                            cursor: 'pointer',
                            dataLabels: {
                                enabled: false
                            },
                            showInLegend: true
                        }
                    };
                    var series = [{
                        type: 'pie',
                        name: 'Time spend',
                        data: [
                            ['Work', 45.0],
                            ['Rest', 55.0],
                        ]
                    }];

                    var json = {};
                    json.chart = chart;
                    json.title = title;
                    json.tooltip = tooltip;
                    json.series = series;
                    json.plotOptions = plotOptions;
                    $('#container').highcharts(json);
                });
            </script>
        </div>
        <div class="col-md-6">
            <div id="container2" style="width: 550px; height: 400px; margin: 0 auto"></div>
            <script language="JavaScript">
                $(document).ready(function () {
                    var chart = {
                        plotBackgroundColor: null,
                        plotBorderWidth: null,
                        plotShadow: false
                    };
                    var title = {
                        text: 'Categories'
                    };
                    var tooltip = {
                        pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
                    };
                    var plotOptions = {
                        pie: {
                            allowPointSelect: true,
                            cursor: 'pointer',
                            dataLabels: {
                                enabled: false
                            },
                            showInLegend: true
                        }
                    };
                    var series = [{
                        type: 'pie',
                        name: 'Time spend',
                        data: [
                            ['Work', 45.0],
                            ['Rest', 55.0],
                        ]
                    }];

                    var json = {};
                    json.chart = chart;
                    json.title = title;
                    json.tooltip = tooltip;
                    json.series = series;
                    json.plotOptions = plotOptions;
                    $('#container2').highcharts(json);
                });
            </script>
        </div>
    </div>
</div>
</body>
</html>
