<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="monster" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<monster:template title="Edit monster">
    <jsp:attribute name="body">
        <div class="container">
            <form:form action="/pa165/monster/submit" modelAttribute="monster">
                <form:hidden path="id"/>
                <div class="row">

                <s:bind path="name">
                    <div class="form-group col-md-6 col-xs-12 ${nameErr ? 'has-error' : ''}">
                        <form:label path="name"><f:message key="monster.name"/></form:label>
                          <c:if test="${nameErr}">
		              <span class="text-danger">Name cannot be empty!</span>
		          </c:if>
                        <form:input cssClass="form-control" path="name"/>
                    </div>
                </s:bind>



                    <s:bind path="hitpoints">
                    <div class="form-group col-md-6 col-xs-12 ${status.error || hpErr ? 'has-error' : ''}">
                        <form:label path="hitpoints"><f:message key="monster.hitpoints"/></form:label>
                        <c:if test="${status.error || hpErr}">
                            <span class="text-danger">Hitpoints must be a positive integer!</span>
                        </c:if>
                        <form:input cssClass="form-control" path="hitpoints"/>

                    </div>
                </s:bind>

                    <s:bind path="damage">
                    <div class="form-group col-md-6 col-xs-12 ${status.error ? 'has-error' : ''}">
                        <form:label path="damage"><f:message key="monster.damage"/></form:label>
                        <c:if test="${status.error}">
                           <span class="text-danger">Damage must be an integer!</span>
                        </c:if>
                        <form:input cssClass="form-control" path="damage"/>
                    </div>
                </s:bind>

                    <s:bind path="size">
                    <div class="form-group col-md-6 col-xs-12 ${sizeErr ? 'has-error' : ''}">
                        <form:label path="size"><f:message key="monster.size"/></form:label>
                        <c:if test="${sizeErr}">
		              <span class="text-danger">Size cannot be empty!</span>
		          </c:if>
                        <form:input cssClass="form-control" path="size"/>
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
</monster:template>