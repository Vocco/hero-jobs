<%--
  Author: Metodej Klang
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="quest" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<f:message var="title" key="quests"/>

<quest:template title="${title}">
    <jsp:attribute name="body">
    <div class="container">
        <sec:authorize access="hasAuthority('ADMIN')">
            <div class="row">
                    <quest:a href="/quest/new" class="btn btn-primary pull-right">
                        <span class="glyphicon glyphicon-plus"></span>
                        Add quest
                    </quest:a>
            </div>
        </sec:authorize>
        <table class="table">
            <thead>
            <tr>
                <th><f:message key="quest.name"/></th>
                <th><f:message key="quest.location"/></th>
                <th><f:message key="quest.reward"/></th>
                <th><f:message key="quest.heroLimit"/></th>
                <th><f:message key="quest.performanceEvaluation"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${quests}" var="quest">
                <tr class="clickable-row" data-href="/quest/view/${quest.id}">

                        <td><quest:a href="/quest/view/${quest.id}"><c:out value="${quest.name}"/></quest:a></td>

                        <td><c:out value="${quest.location}"/></td>
                	<td><c:out value="${quest.reward}"/></td>
                	<td><c:out value="${quest.heroLimit}"/></td>
                	<td><c:out value="${quest.performanceEvaluation}"/></td>
                    </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

</jsp:attribute>
</quest:template>