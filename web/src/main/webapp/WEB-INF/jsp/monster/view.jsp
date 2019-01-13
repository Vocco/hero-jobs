<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="monster" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<f:message var="title" key="monsters"/>

<monster:template title="${title}">
    <jsp:attribute name="body">
    <monster:a href="/monster/all" class="btn btn-info"><f:message key="back"/></monster:a>
    <div class="container">
        <h1>${monster.name}</h1>
        <c:if test="${canEdit}">
                <div class="row">
                    <monster:a href="/monster/edit/${monster.id}" class="btn btn-primary">
                        <span class="glyphicon glyphicon-pencil"></span>
                        <f:message key="edit"/>
                    </monster:a>
                </div>
            </c:if>
        <ul>
            <li><f:message key="monster.hitpoints"/>: ${monster.hitpoints}</li>
            <li><f:message key="monster.damage"/>: ${monster.damage}</li>
            <li><f:message key="monster.size"/>: ${monster.size}</li>
        </ul>
        <h2><f:message key="weaknesses"/></h2>
        <ol>
        <c:forEach items="${weaknesses}" var="weakness">
            <li>${weakness.name} - ${weakness.level}</li>
        </c:forEach>
        </ol>
        <h2><f:message key="strengths"/></h2>
        <ol>
        <c:forEach items="${strengths}" var="strength">
            <li>${strength.name} - ${strength.level}</li>
        </c:forEach>
        </ol>


    </div>

</jsp:attribute>
</monster:template>