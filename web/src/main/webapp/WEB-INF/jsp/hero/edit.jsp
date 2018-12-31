<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="hero" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<hero:template title="Edit hero">
    <jsp:attribute name="body">
        <div class="container">
            <form:form action="/pa165/hero/submit" modelAttribute="hero">
                <form:hidden path="id"/>
                <div class="row">

                <s:bind path="name">
                    <div class="form-group col-md-6 col-xs-12 ${status.error ? 'has-error' : ''}">
                        <form:label path="name"><f:message key="hero.name"/></form:label>
                        <span class="text-danger"><c:out value="${status.errorMessage}"/></span>
                        <form:input cssClass="form-control" path="name"/>
                    </div>
                </s:bind>

                    <s:bind path="alive">
                    <div class="form-group col-md-6 col-xs-12 ${status.error ? 'has-error' : ''}">
                        <form:label path="alive"><f:message key="hero.status"/></form:label>
                        <span class="text-danger"><c:out value="${status.errorMessage}"/></span>
                        <form:checkbox cssClass="form-control" path="alive"/>
                    </div>
                </s:bind>

                    <s:bind path="gold">
                    <div class="form-group col-md-6 col-xs-12 ${status.error ? 'has-error' : ''}">
                        <form:label path="gold"><f:message key="hero.gold"/></form:label>
                        <span class="text-danger"><c:out value="${status.errorMessage}"/></span>
                        <form:input cssClass="form-control" path="gold"/>
                    </div>
                </s:bind>

                    <s:bind path="hitpoints">
                    <div class="form-group col-md-6 col-xs-12 ${status.error ? 'has-error' : ''}">
                        <form:label path="hitpoints"><f:message key="hero.hitpoints"/></form:label>
                        <span class="text-danger"><c:out value="${status.errorMessage}"/></span>
                        <form:input cssClass="form-control" path="hitpoints"/>
                    </div>
                </s:bind>

                    <s:bind path="damage">
                    <div class="form-group col-md-6 col-xs-12 ${status.error ? 'has-error' : ''}">
                        <form:label path="damage"><f:message key="hero.damage"/></form:label>
                        <span class="text-danger"><c:out value="${status.errorMessage}"/></span>
                        <form:input cssClass="form-control" path="damage"/>
                    </div>
                </s:bind>

                    <s:bind path="might">
                    <div class="form-group col-md-6 col-xs-12 ${status.error ? 'has-error' : ''}">
                        <form:label path="might"><f:message key="hero.might"/></form:label>
                        <span class="text-danger"><c:out value="${status.errorMessage}"/></span>
                        <form:input cssClass="form-control" path="might"/>
                    </div>
                </s:bind>

                    <s:bind path="agility">
                    <div class="form-group col-md-6 col-xs-12 ${status.error ? 'has-error' : ''}">
                        <form:label path="agility"><f:message key="hero.agility"/></form:label>
                        <span class="text-danger"><c:out value="${status.errorMessage}"/></span>
                        <form:input cssClass="form-control" path="agility"/>
                    </div>
                </s:bind>

                    <s:bind path="magic">
                    <div class="form-group col-md-6 col-xs-12 ${status.error ? 'has-error' : ''}">
                        <form:label path="magic"><f:message key="hero.magic"/></form:label>
                        <span class="text-danger"><c:out value="${status.errorMessage}"/></span>
                        <form:input cssClass="form-control" path="magic"/>
                    </div>
                </s:bind>

                </div>

                <div class="row">
                    <button type="submit" class="btn btn-primary pull-right">
                        <span class="glyphicon glyphicon-floppy-disk"></span>
                        <f:message key="save"/>
                    </button>
                </div>
            </form:form>
        </div>
    </jsp:attribute>
</hero:template>

