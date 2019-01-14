<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="hero" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<f:message var="title" key="index"/>

<hero:template title="${title}">
<jsp:attribute name="body">

    <h1>Welcome to Hero Jobs</h1>
    <p>A SpringMVC Application built for the purposes of PA165 course.</p>
    <p>Dataset used by this application is procedurally generated.</p>
    <p>
        Authors:
        <ul>
            <li>Vojtěch Krajňanský</li>
            <li>Michal Pavúk</li>
            <li>Metoděj Klang</li>
            <li>Jakub Strmeň</li>
        </ul>
    </p>

</jsp:attribute>
</hero:template>
