<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Error 403</title>
</head>
<body>
Error 403 - Forbidden
<f:message key="reason"/>: <c:out value="${message}"/>
</body>
</html>
