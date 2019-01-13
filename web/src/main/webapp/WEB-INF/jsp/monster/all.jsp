<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="monster" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<f:message var="title" key="monsters"/>

<monster:template title="${title}">
    <jsp:attribute name="body">
    <div class="container">
        <sec:authorize access="hasAuthority('ADMIN')">
            <div class="row">
                    <monster:a href="/monster/new" class="btn btn-primary pull-right">
                        <span class="glyphicon glyphicon-plus"></span>
                        Add monster
                    </monster:a>
            </div>
        </sec:authorize>
        <table class="table">
            <thead>
            <tr>
                <th><f:message key="monster.name"/></th>

                <th><i class="ra ra-muscle-up ra-fw"></i>&nbsp;<f:message key="monster.size"/></th>
                <th><i class="ra ra-health ra-fw"></i>&nbsp;<f:message key="monster.hitpoints"/></th>
                <th><i class="ra ra-crossed-swords ra-fw">&nbsp;</i><f:message key="monster.damage"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${monsters}" var="monster">
                    <tr class="clickable-row" data-href="/monster/view/${monster.id}">

                        <td><monster:a href="/monster/view/${monster.id}"><c:out value="${monster.name}"/></monster:a></td>

                        <td><c:out value="${monster.size}"/></td>
                	<td><c:out value="${monster.hitpoints}"/></td>
                	<td><c:out value="${monster.damage}"/></td>
                    </tr>

                </c:forEach>
            </tbody>
        </table>
    </div>

</jsp:attribute>
</monster:template>
