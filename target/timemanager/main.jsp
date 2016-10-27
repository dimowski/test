<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Locale" %>
<%@ page import="org.apache.logging.log4j.Logger" %>
<%@ page import="org.apache.logging.log4j.LogManager" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<html>
<head>
    <title>Track time</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/tabview.css">
    <script type="text/javascript" src="javascript/timestamp.js"></script>
    <script src="javascript/yui-min.js"></script>
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
                <li class="active"><a href="main.jsp">Track</a></li>
                <li><a href="manage.jsp">Manage</a></li>
                <li><a href="MainServlet?command=activity">Activity</a></li>
                <li><a href="login.html">Logout</a></li>
            </ul>
        </div>
    </nav>

    <h3 class="text-center"><%= new java.text.SimpleDateFormat("EEEE, dd MMMM y", Locale.ENGLISH).format(new java.util.Date()) %>
    </h3>
    <div class="page-header">
        <h4>ADD TRACK LOG</h4>
    </div>
    <div class="row">
        <div class="col-md-8">
            <form id="timeForm" method="post" action="MainServlet?command=ADD_ACTIVITY">
                <div id="links" style="padding-left:20px;">
                    <input type="hidden" id="selecteddate" name="targetDate">
                </div>
                <div class="row">
                    <div class="col-sm-7 col-sm-offset-3">
                        <div class="form-group">
                            <label for="category">Category</label>
                            <select class="form-control" id="category" name="category">
                                <option></option>
                                <c:forEach var="tempCategory" items="${CATEGORY_LIST}">
                                    <option value="${tempCategory.id}">${tempCategory.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="subcategory">Subcategory</label>
                            <select class="form-control" id="subcategory" name="subcategory">
                                <option></option>
                                <c:forEach var="tempSubcategory" items="${SUBCATEGORY_LIST}">
                                    <option value="${tempSubcategory.id}">${tempSubcategory.name}</option>
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
                                    <button class="btn btn-default" type="button" onclick="displayStartTime()">Now
                                    </button>
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
                                    <button class="btn btn-default" type="button" onclick="displayFinishTime()">Now
                                    </button>
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
                </div>
            </form>
        </div>
        <div class="col-md-4 yui3-skin-sam">

            <style>
                .yui3-button {
                    margin: 10px 0px 10px 0px;
                    color: #fff;
                    background-color: #3476b7;
                }
            </style>

            <div id="demo" class="yui3-skin-sam yui3-g"> <!-- You need this skin class -->

                <div id="leftcolumn" class="yui3-u">
                    <!-- Container for the calendar -->
                    <div id="mycalendar"></div>
                </div>
                <div id="rightcolumn" class="yui3-u">

                </div>
            </div>

            <script type="text/javascript">
                YUI().use('calendar', 'datatype-date', 'cssbutton', function (Y) {

                    // Create a new instance of calendar, placing it in
                    // #mycalendar container, setting its width to 340px,
                    // the flags for showing previous and next month's
                    // dates in available empty cells to true, and setting
                    // the date to today's date.
                    var calendar = new Y.Calendar({
                        contentBox: "#mycalendar",
                        width: '340px',
                        showPrevMonth: true,
                        showNextMonth: true,
                        date: new Date(2016, 09, 27)
                    }).render();

                    // Get a reference to Y.DataType.Date
                    var dtdate = Y.DataType.Date;

                    // Listen to calendar's selectionChange event.
                    calendar.on("selectionChange", function (ev) {

                        // Get the date from the list of selected
                        // dates returned with the event (since only
                        // single selection is enabled by default,
                        // we expect there to be only one date)
                        var newDate = ev.newSelection[0];

                        // Format the date and output it to a DOM
                        // element.
                        Y.one("#selecteddate").setHTML(dtdate.format(newDate));
                        window.location = "MainServlet?command=TRACK&value=" + dtdate.format(newDate).toString();
                    });

                    // When the 'Show Previous Month' link is clicked,
                    // modify the showPrevMonth property to show or hide
                    // previous month's dates
                    Y.one("#togglePrevMonth").on('click', function (ev) {
                        ev.preventDefault();
                        calendar.set('showPrevMonth', !(calendar.get("showPrevMonth")));
                    });

                    // When the 'Show Next Month' link is clicked,
                    // modify the showNextMonth property to show or hide
                    // next month's dates
                    Y.one("#toggleNextMonth").on('click', function (ev) {
                        ev.preventDefault();
                        calendar.set('showNextMonth', !(calendar.get("showNextMonth")));
                    });
                });
            </script>

        </div>

    </div>
    <div class="row">
        <c:if test="${empty DAILY_ACTIVITY}">
            <div class="page-header"><h4>TRACKED</h4></div>
            No time tracked for this day.
        </c:if>
        <c:if test="${not empty DAILY_ACTIVITY}">
            <div class="page-header">
                <h4>TRACKED</h4>
            </div>
            <c:forEach var="activity" items="${DAILY_ACTIVITY}">
                <div class="row">
                    <div class="col-md-5">
                        <div><h4>${activity.category} - ${activity.subcategory}</h4></div>
                        <div><h5>${activity.description}</h5></div>
                    </div>
                    <div class="col-md-5">
                        <div><h4>${activity.duration}</h4></div>
                        <div><h4>${activity.start} - ${activity.finish}</h4></div>
                    </div>
                </div>
            </c:forEach>
        </c:if>
    </div>
</div>
</body>
</html>