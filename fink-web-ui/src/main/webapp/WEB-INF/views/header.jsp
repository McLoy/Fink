<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Bootstrap, from Twitter</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <link href="/resources/bootstrap/css/bootstrap.css" rel="stylesheet">
    <link href="/resources/css/main.css" rel="stylesheet">
    <link rel="stylesheet" href="/resources/bootstrap/widgets/bootstrap-datetimepicker/css/bootstrap-datetimepicker.css" />
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
    <%--<script src="http://code.jquery.com/jquery-latest.js"></script>--%>
    <script src="/resources/js/jquery-3.1.1.js"></script>
    <script src="/resources/js/moment/moment-with-locales.js"></script>
    <script src="/resources/bootstrap/js/bootstrap.js"></script>
    <script src="/resources/bootstrap/widgets/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script>
    <script src="/resources/js/main.js"></script>
    <title>${title}</title>
</head>
<body>
<div class="container">
    <nav class="navbar navbar-inverse">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="/">Fink</a>
            </div>
            <div class="collapse navbar-collapse" id="myNavbar">
                <ul class="nav navbar-nav">
                    <%--<li class="active"><a href="#">Home</a></li>--%>
                    <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">Scheduler<span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="/job">Jobs</a></li>
                            <li><a href="/trigger">Triggers</a></li>
                            <li><a href="/calendar">Calendars</a></li>
                        </ul>
                    </li>
                </ul>
                <ul class="nav navbar-nav">
                    <%--<li class="active"><a href="#">Home</a></li>--%>
                    <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">Administration<span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="/user">Users</a></li>
                            <li><a href="/role">Roles</a></li>
                            <li><a href="/preference">Preferences</a></li>
                        </ul>
                    </li>
                </ul>
                <sec:authorize access="isAuthenticated()">
                    <ul class="nav navbar-nav navbar-right">
                        <li>
                            <p class="navbar-text" style="text-transform: uppercase"><a href="#" class="navbar-link"><sec:authentication
                                    property="principal.username"/></a></p>
                        </li>
                        <li>
                            <a href="/logout"><span class="glyphicon glyphicon-log-out"></span> Logout</a>
                        </li>
                    </ul>
                </sec:authorize>
            </div>
        </div>
    </nav>

    <div class="page-header">
        <h1>${title}</h1>
    </div>