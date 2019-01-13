<%--
  Author: Metodej Klang
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="quest" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<quest:template title="Edit quest">
    <jsp:attribute name="body">
        <div class="container">
            <form:form action="/pa165/quest/submit" modelAttribute="quest">
                <form:hidden path="id"/>
                <div class="row">

                <s:bind path="name">
                    <div class="form-group col-md-6 col-xs-12 ${nameErr ? 'has-error' : ''}">
                        <form:label path="name"><f:message key="quest.name" /></form:label>
                        <c:if test="${nameErr}">
                            <span class="text-danger">Name cannot be empty.</span>
                         </c:if>
                        <form:input cssClass="form-control" path="name"/>
                    </div>
                </s:bind>

                    <s:bind path="location">
                    <div class="form-group col-md-6 col-xs-12 ${locErr ? 'has-error' : ''}">
                        <form:label path="location"><f:message key="quest.location" /></form:label>
                        <c:if test="${locErr}">
                            <span class="text-danger">Location cannot be empty.</span>
                         </c:if>
                        <form:input cssClass="form-control" path="location" />
                    </div>
                </s:bind>

                    <s:bind path="reward">
                    <div class="form-group col-md-6 col-xs-12 ${status.error || rewardErr ? 'has-error' : ''}">
                        <form:label path="reward"><f:message key="quest.reward" /></form:label>
                        <c:if test="${status.error || rewardErr}">
                            <span class="text-danger">Quest reward must be a positive integer.</span>
                         </c:if>
                        <form:input cssClass="form-control" path="reward" />
                    </div>
                </s:bind>

                    <s:bind path="performanceEvaluation">
                    <div class="form-group col-md-6 col-xs-12 ${status.error || perfErr ? 'has-error' : ''}">
                        <form:label path="performanceEvaluation"><f:message key="quest.performanceEvaluation" /></form:label>
                        <c:if test="${status.error || perfErr}">
                            <span class="text-danger">Performance Evaluation must be an integer between 0 and 5.</span>
                         </c:if>
                        <form:input cssClass="form-control" path="performanceEvaluation" />
                    </div>
                </s:bind>

                </div>

                <div class="row">
                    <button type="submit" class="btn btn-primary pull-right">
                        <span class="glyphicon glyphicon-floppy-disk"></span>
                        <f:message key="save" />
                    </button>
                </div>
            </form:form>
        </div>
    </jsp:attribute>
</quest:template>


