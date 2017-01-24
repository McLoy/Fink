<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="../header.jsp"/>
<form method="post" action="/user/${entity.id==null?'newuser':entity.id}" class="form-horizontal">
    <input type="hidden" id="id" name="id" value="${entity.id}"/>
    <div class="form-group">
        <label for="name" class="control-label col-xs-2">User:</label>
        <div class="col-xs-4">
            <textarea id="name" name="name" rows="1" class="form-control">${entity.name}</textarea>
        </div>
    </div>
    <div class="form-group">
        <label for="email" class="control-label col-xs-2">Email:</label>
        <div class="col-xs-4">
            <textarea id="email" name="email" rows="1" class="form-control">${entity.email}</textarea>
        </div>
    </div>
    <div class="form-group ${(errors.password!=null) ? 'has-error':''}">
        <label for="password" class="control-label col-xs-2">Password:</label>
        <div class="col-xs-4">
            <input type="password" id="password" name="password" class="form-control">
        <c:forEach var="m" items="${errors}">
            <c:if test="${m.key == 'password'}">
                <span class="help-block">${m.value}</span>
            </c:if>
        </c:forEach>
        </div>
    </div>
    <c:if test="${entity.id != null}">
        <div class="form-group ${(errors.newpassword!=null) ? 'has-error':''}">
            <label for="newpassword" class="control-label col-xs-2">New password:</label>
            <div class="col-xs-4">
                <input type="password" id="newpassword" name="newpassword" class="form-control">
                <c:forEach var="m" items="${errors}">
                    <c:if test="${m.key == 'newpassword'}">
                        <span class="help-block">${m.value}</span>
                    </c:if>
                </c:forEach>
            </div>
        </div>
    </c:if>
    <div class="form-group ${(errors.confirmpassword!=null) ? 'has-error':''}">
        <label for="confirmpassword" class="control-label col-xs-2">Confirm password:</label>
        <div class="col-xs-4">
            <input type="password" id="confirmpassword" name="confirmpassword" class="form-control">
            <c:forEach var="m" items="${errors}">
                <c:if test="${m.key == 'confirmpassword'}">
                    <span class="help-block">${m.value}</span>
                </c:if>
            </c:forEach>
        </div>
    </div>
    <div class="form-group">
        <div class="col-xs-offset-2 col-xs-4">
            <div class="check-box">
                <input type="checkbox" id="enabled" name="enabled" ${entity.enabled?"checked":""}/>
                <label for="enabled">Enabled</label>
            </div>
        </div>
    </div>
    <div class="form-group">
        <div class="col-xs-offset-2 col-xs-4">
            <button type="submit" class="btn btn-success">Save</button>
            <a href='/user' class="btn btn-default" role="button">Cancel</a>
        </div>
    </div>
</form>
<jsp:include page="../footer.jsp"/>