<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="hero" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<f:message var="title" key="heroes"/>

<hero:template title="${title}">
    <jsp:attribute name="body">
    <div class="container">
        <sec:authorize access="hasAuthority('ADMIN')">
            <div class="row">
                    <hero:a href="/hero/new" class="btn btn-primary pull-right">
                        <span class="glyphicon glyphicon-plus"></span>
                        Add hero
                    </hero:a>
            </div>
        </sec:authorize>
        <table class="table">
            <thead>
            <tr>
                <th><f:message key="hero.status"/></th>
                <th><f:message key="hero.name"/></th>

                <th><f:message key="hero.gold"/></th>
                <th><f:message key="hero.hitpoints"/></th>
                <th><f:message key="hero.damage"/></th>

                <th><f:message key="hero.might"/></th>
                <th><f:message key="hero.agility"/></th>
                <th><f:message key="hero.magic"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${heroes}" var="hero">
                    <tr class="clickable-row" data-href="/hero/view/${hero.id}">
                        <c:if test="${hero.alive}">
                            <td><span class="glyphicon glyphicon-heart"></span></td>
                        </c:if>
                        <c:if test="${!hero.alive}">
                            <td><span class="glyphicon glyphicon-exclamation-sign"></span></td>
                        </c:if>
                        <td><c:out value="${hero.getName}"/></td>

                        <td><c:out value="${hero.gold}"/></td>
                        <td><c:out value="${hero.hitpoints}"/></td>
                        <td><c:out value="${hero.damage}"/></td>

                        <td><c:out value="${hero.might}"/></td>
                        <td><c:out value="${hero.agility}"/></td>
                        <td><c:out value="${hero.magic}"/></td>
                    </tr>

                </c:forEach>
            </tbody>
        </table>
    </div>

</jsp:attribute>
</hero:template>
