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
                    <div class="form-group col-xs-4 ${status.error ? 'has-error' : ''}">
                        <form:label path="gold"><f:message key="hero.gold"/></form:label>
                        <span class="text-danger"><c:out value="${status.errorMessage}"/></span>
                        <form:input cssClass="form-control" path="gold"/>
                    </div>
                </s:bind>

                    <s:bind path="hitpoints">
                    <div class="form-group col-xs-4 ${status.error ? 'has-error' : ''}">
                        <form:label path="hitpoints"><f:message key="hero.hitpoints"/></form:label>
                        <span class="text-danger"><c:out value="${status.errorMessage}"/></span>
                        <form:input cssClass="form-control" path="hitpoints"/>
                    </div>
                </s:bind>

                    <s:bind path="damage">
                    <div class="form-group col-xs-4 ${status.error ? 'has-error' : ''}">
                        <form:label path="damage"><f:message key="hero.damage"/></form:label>
                        <span class="text-danger"><c:out value="${status.errorMessage}"/></span>
                        <form:input cssClass="form-control" path="damage"/>
                    </div>
                </s:bind>

                    <s:bind path="might">
                    <div class="form-group col-xs-4 ${status.error ? 'has-error' : ''}">
                        <form:label path="might"><f:message key="hero.might"/></form:label>
                        <span class="text-danger"><c:out value="${status.errorMessage}"/></span>
                        <form:input cssClass="form-control" path="might"/>
                    </div>
                </s:bind>

                    <s:bind path="agility">
                    <div class="form-group col-xs-4 ${status.error ? 'has-error' : ''}">
                        <form:label path="agility"><f:message key="hero.agility"/></form:label>
                        <span class="text-danger"><c:out value="${status.errorMessage}"/></span>
                        <form:input cssClass="form-control" path="agility"/>
                    </div>
                </s:bind>

                    <s:bind path="magic">
                    <div class="form-group col-xs-4 ${status.error ? 'has-error' : ''}">
                        <form:label path="magic"><f:message key="hero.magic"/></form:label>
                        <span class="text-danger"><c:out value="${status.errorMessage}"/></span>
                        <form:input cssClass="form-control" path="magic"/>
                    </div>
                </s:bind>
                </div>

                <h3>Skills</h3>

                <div class="row">
                    <c:forEach items="${hero.skills}" var="skill" varStatus="status">
                        <div class="col col-md-4 col-xs-6" id="skill_${status.index}">
                            <label for="skills[${status.index}].name"><f:message key="hero.name"/></label>
                            <input class="form-control" name="skills[${status.index}].name" value="${skill.name}"/>
                            <input type="hidden" name="skills[${status.index}].id" value="${skill.id}"/>

                            <label><f:message key="hero.affinities"/></label>
                            <c:forEach items="${skill.affinities}" var="affinity" varStatus="statusA">
                                <div class="row affinity-${skill.id}">
                                    <input class="col col-xs-8" name="affinities[${statusA.index}].name"
                                           value="${affinity.name}"/>
                                    <input class="col col-xs-4" name="affinities[${statusA.index}].level"
                                           value="${affinity.level}" type="number"/>
                                    <input type="hidden" name="affinities[${statusA.index}].id" value="${affinity.id}"/>
                                </div>
                            </c:forEach>
                                <%--<button class="btn add-affinity" data-next="${skill.affinities.size()}"><f:message key="hero.addaffinity" /></button>--%>

                        </div>
                    </c:forEach>
                </div>

                <div class="row">
                    <button type="submit" class="btn btn-primary pull-right">
                        <span class="glyphicon glyphicon-floppy-disk"></span>
                        <f:message key="save"/>
                    </button>
                </div>
            </form:form>
        </div>
        <script>
            document.querySelectorAll('input').forEach(function (e) {
                e.addEventListener('click', function (ev) {
                    ev.preventDefault();

                    var next = e.dataset['next'];

                    var el = document.createElement('input');
                    el.classList.add('col', 'col-xs-8');
                    el.setAttribute('name', 'affinities[' + next + '].name');
                    ev.target.prepend(el);

                    el = document.createElement('input');
                    el.classList.add('col', 'col-xs-4');
                    el.setAttribute('name', 'affinities[' + next + '].level');
                    ev.target.prepend(el);

                    e.dataset['next'] = next + 1;

                    return false;
                })
            })
        </script>
    </jsp:attribute>
</hero:template>

