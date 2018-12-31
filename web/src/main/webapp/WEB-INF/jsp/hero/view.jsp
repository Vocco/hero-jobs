<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="hero" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<f:message var="title" key="heroes"/>

<hero:template title="${title}">
    <jsp:attribute name="body">
    <div class="container">
        <h1>${hero.name}</h1>
        <c:if test="${canEdit}">
                <div class="row">
                    <my:a href="/hero/edit/${hero.id}" class="btn btn-primary">
                        <span class="glyphicon glyphicon-pencil"></span>
                        <f:message key="edit"/>
                    </my:a>
                </div>
            </c:if>
        <ul>
            <li><f:message key="hero.gold"/>: ${hero.gold}</li>
            <li><f:message key="hero.status"/>: ${hero.alive}</li>
            <li><f:message key="hero.hitpoints"/>: ${hero.hitpoints}</li>
            <li><f:message key="hero.damage"/>: ${hero.damage}</li>
            <li><f:message key="hero.might"/>: ${hero.might}</li>
            <li><f:message key="hero.agility"/>: ${hero.agility}</li>
            <li><f:message key="hero.magic"/>: ${hero.magic}</li>
            <li><f:message key="hero.currentQuest"/>: <hero:a
                    href="/quest/view/${hero.quest.id}">${hero.quest.name}</hero:a></li>
        </ul>
        <h2><f:message key="skills"/></h2>
        <ol>
        <c:forEach items="${skills}" var="skill">
            <li>${skill.name} (<em>${skill.baseDamage} <f:message key="hero.damage"/></em>)</li>
            <ul>
            <c:forEach items="${skill.affinities}" var="affinity">
                <li>${affinity.name} - ${affinity.level}</li>
            </c:forEach>
            </ul>
        </c:forEach>
        </ol>
    </div>

</jsp:attribute>
</hero:template>
