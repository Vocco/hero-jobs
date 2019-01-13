<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="hero" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<f:message var="title" key="sign_in"/>

<hero:template title="${title}">
<jsp:attribute name="body">

    <div class="container">
        <div class="col-md-5 offset-md-3">
            <form method="POST" role="form">
                <div class="form-group">
                    <label for="username"><f:message key="username"/>:</label>
                    <input type="text" name="username" id="username" class="form-control" required/>
                </div>
                <div class="form-group">
                    <label for="password"><f:message key="password"/>:</label>
                    <input type="password" name="password" id="password" class="form-control" required/>
                </div>
                <div class="form-actions">
                    <input type="submit" value='<f:message key="sign_in"/>'/>
                </div>
            </form>
            <br>
        </div>
    </div>

</jsp:attribute>
</hero:template>
