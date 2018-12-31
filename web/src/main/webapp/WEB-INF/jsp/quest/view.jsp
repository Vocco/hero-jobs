<%--
  Author: Metodej Klang
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="quest" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<f:message var="title" key="quests"/>

<quest:template title="${title}">
    <jsp:attribute name="body">
    <div class="container">
        <h1>${quest.name}</h1>
        <sec:authorize access="hasAuthority('ADMIN')">
                <div class="row">
                    <quest:a href="/quest/edit/${quest.id}" class="btn btn-primary">
                        <span class="glyphicon glyphicon-pencil"></span>
                        <f:message key="edit" />
                    </quest:a>
                </div>
        </sec:authorize>
        <ul>
            <li><f:message key="quest.location" />: ${quest.location}</li>
            <li><f:message key="quest.reward" />: ${quest.reward}</li>
            <li><f:message key="quest.heroLimit" />: ${quest.heroLimit}</li>
            <li><f:message key="quest.state" />: ${quest.state}</li>
            <li><f:message key="quest.performanceEvaluation" />: ${quest.state}</li>
        </ul>
        <h2><f:message key="assignedHeroes" /></h2>
        <ol>
        <c:forEach items="${quest.assignedHeroes}" var="hero">
            <li>${hero.name}</li>
        </c:forEach>
        </ol>
        <h2><f:message key="deadHeroes" /></h2>
        <ol>
        <c:forEach items="${quest.deadHeroes}" var="hero">
            <li>${hero.name}</li>
        </c:forEach>
        </ol>
        <h2><f:message key="monsters" /></h2>
        <ol>
        <c:forEach items="${quest.monsters}" var="monster">
            <li>${monster.name}</li>
        </c:forEach>
        </ol>

    </div>

</jsp:attribute>
</quest:template>