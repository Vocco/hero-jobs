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
                    <div class="form-group col-md-6 col-xs-12 ${status.error ? 'has-error' : ''}">
                        <form:label path="name"><f:message key="quest.name" /></form:label>
                        <span class="text-danger"><c:out value="${status.errorMessage}"/></span>
                        <form:input cssClass="form-control" path="name"/>
                    </div>
                </s:bind>

                    <s:bind path="location">
                    <div class="form-group col-md-6 col-xs-12 ${status.error ? 'has-error' : ''}">
                        <form:label path="location"><f:message key="quest.location" /></form:label>
                        <span class="text-danger"><c:out value="${status.errorMessage}"/></span>
                        <form:input cssClass="form-control" path="location" />
                    </div>
                </s:bind>

                    <s:bind path="reward">
                    <div class="form-group col-md-6 col-xs-12 ${status.error ? 'has-error' : ''}">
                        <form:label path="reward"><f:message key="quest.reward" /></form:label>
                        <span class="text-danger"><c:out value="${status.errorMessage}"/></span>
                        <form:input cssClass="form-control" path="reward" />
                    </div>
                </s:bind>

                    <s:bind path="state">
                    <div class="form-group col-md-6 col-xs-12 ${status.error ? 'has-error' : ''}">
                        <form:label path="state"><f:message key="quest.state" /></form:label>
                        <span class="text-danger"><c:out value="${status.errorMessage}"/></span>
                        <form:input cssClass="form-control" path="heroLimit" />
                    </div>
                </s:bind>

                    <s:bind path="performanceEvaluation">
                    <div class="form-group col-md-6 col-xs-12 ${status.error ? 'has-error' : ''}">
                        <form:label path="performanceEvaluation"><f:message key="quest.performanceEvaluation" /></form:label>
                        <span class="text-danger"><c:out value="${status.errorMessage}"/></span>
                        <form:input cssClass="form-control" path="damage" />
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


