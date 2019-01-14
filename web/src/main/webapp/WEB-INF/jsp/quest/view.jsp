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
        <c:if test="${canEdit}">
                <div class="row">
                    <quest:a href="/quest/edit/${quest.id}" class="btn btn-primary">
                        <span class="glyphicon glyphicon-pencil"></span>
                        <f:message key="edit" />
                    </quest:a>
                </div>
        </c:if>
        <ul>
            <li><span class="glyphicon glyphicon-map-marker"></span>&nbsp;<f:message
                    key="quest.location"/>: ${quest.location}</li>
            <li><i class="ra ra-fw ra-gem"></i>&nbsp;<f:message key="quest.reward"/>: ${quest.reward}</li>
            <li><i class="ra ra-fw ra-double-team"></i>&nbsp;<f:message key="quest.heroLimit"/>: ${quest.heroLimit}</li>
            <li><i class="ra ra-fw ra-quill-ink"></i>&nbsp;<f:message key="quest.state"/>: ${quest.state.name()}</li>
            <li><i class="ra ra-fw ra-crown"></i>&nbsp;<f:message
                    key="quest.performanceEvaluation"/>: ${quest.performanceEvaluation}</li>
        </ul>
        <h3><i class="ra ra-fw ra-double-team"></i>&nbsp;<f:message key="assignedHeroes"/></h3>
        <ol>
            <c:choose>
                <c:when test="${quest.assignedHeroes.size() > 0}">
                    <c:forEach items="${quest.assignedHeroes}" var="hero">
                        <li>${hero.name}</li>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <f:message key="quest.noheroes"/>
                </c:otherwise>
            </c:choose>
        </ol>
        <h3><i class="ra ra-fw ra-tombstone"></i>&nbsp;<f:message key="deadHeroes"/></h3>
        <ol>
            <c:choose>
                <c:when test="${quest.deadHeroes.size() > 0}">
                    <c:forEach items="${quest.deadHeroes}" var="hero">
                        <li>${hero.name}</li>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <f:message key="quest.nodeadheroes"/>
                </c:otherwise>
            </c:choose>
        </ol>
        <h3><i class="ra ra-fw ra-dinosaur"></i>&nbsp;<f:message key="monsters"/></h3>
        <ol>
            <c:choose>
                <c:when test="${quest.monsters.size() > 0}">
                    <c:forEach items="${quest.monsters}" var="questmonster">
                        <li>${questmonster.monster.name} - ${questmonster.monsterCount}</li>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <f:message key="quest.nomonsters"/>
                </c:otherwise>
            </c:choose>
        </ol>

    </div>

    <quest:a href="/quest/all" class="btn btn-info"><f:message key="back"/></quest:a>
</jsp:attribute>
</quest:template>
