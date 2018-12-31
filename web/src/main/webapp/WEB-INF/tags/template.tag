<!DOCTYPE html>
<%@ tag pageEncoding="utf-8" dynamic-attributes="dynattrs" trimDirectiveWhitespaces="true" %>
<%@ attribute name="title" required="false" %>
<%@ attribute name="head" fragment="true" %>
<%@ attribute name="script" fragment="true" %>
<%@ attribute name="body" fragment="true" required="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="hero" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="monster" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="quest" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html lang="${pageContext.request.locale}">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><c:out value="${title}"/></title>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css"
          integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"
            integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k"
            crossorigin="anonymous"></script>

    <jsp:invoke fragment="head"/>
</head>
<body>

<nav class="navbar navbar-inverse navbar-static-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li><hero:a href="/"><f:message key="index"/></hero:a></li>
                <li><hero:a href="/heroes"><f:message key="heroes"/></hero:a></li>
                <li><monster:a href="/monsters"><f:message key="monsters"/></monster:a></li>
                <li><quest:a href="/quests"><f:message key="quests"/></quest:a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <security:authorize access="!isAuthenticated()">
                    <li><hero:a href="/login"><f:message key="sign_in"/></hero:a></li>
                </security:authorize>

                <security:authorize access="isAuthenticated()">
                    <li><a><security:authentication property="principal"/></a></li>
                    <li><hero:a href="/logout"><f:message key="sign_in"/></hero:a></li>
                </security:authorize>
            </ul>
        </div>
    </div>
</nav>

<div class="container">

    <!-- page title -->
    <c:if test="${not empty title}">
        <div class="page-header">
            <h1><c:out value="${title}"/></h1>
        </div>
    </c:if>

    <!-- page body -->
    <jsp:invoke fragment="body"/>

    <footer class="footer">
        <p>&copy;&nbsp;<%=java.time.Year.now().toString()%><f:message key="footer"/></p>
    </footer>
</div>
<jsp:invoke fragment="script"/>
</body>
</html>
