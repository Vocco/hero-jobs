<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="hero" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<f:message var="title" key="heroes"/>

<hero:template title="${title}">
    <jsp:attribute name="body">
    <div class="container">
        <h2>${hero.name}</h2>
        <c:if test="${canEdit}">
                <div class="row">
                    <hero:a href="/hero/edit/${hero.id}" class="btn btn-primary">
                        <span class="glyphicon glyphicon-pencil"></span>
                        <f:message key="edit"/>
                    </hero:a>
                </div>
            </c:if>
        <ul style="list-style: none;">
            <li><i class="ra ra-fw ra-gold-bar"></i>&nbsp;<f:message key="hero.gold"/>: ${hero.gold}</li>
            <li><i class="ra ra-fw ra-hearts"></i>&nbsp;<f:message key="hero.status"/>:
                <c:if test="${hero.alive}"><f:message key="hero.alive"/></c:if>
                <c:if test="${!hero.alive}"><f:message key="hero.dead"/></c:if>
            </li>
            <li><i class="ra ra-fw ra-health"></i>&nbsp;<f:message key="hero.hitpoints"/>: ${hero.hitpoints}</li>
            <li><i class="ra ra-fw ra-crossed-swords"></i>&nbsp;<f:message key="hero.damage"/>: ${hero.damage}</li>
            <li><i class="ra ra-fw ra-muscle-up"></i>&nbsp;<f:message key="hero.might"/>: ${hero.might}</li>
            <li><i class="ra ra-fw ra-player-dodge"></i>&nbsp;<f:message key="hero.agility"/>: ${hero.agility}</li>
            <li><i class="ra ra-fw ra-aura"></i>&nbsp;<f:message key="hero.magic"/>: ${hero.magic}</li>
            <li><i class="ra ra-fw ra-quill-ink"></i>&nbsp;<f:message key="hero.currentQuest"/>:
                <c:if test="${hero.quest != null}">
                    <hero:a href="/quest/view/${hero.quest.id}">${hero.quest.name}</hero:a>
                </c:if>
                <c:if test="${hero.quest == null}">
                    None
                </c:if>
            </li>
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
