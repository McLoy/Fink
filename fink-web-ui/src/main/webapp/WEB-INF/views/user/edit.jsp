<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="../header.jsp"/>
<form method="post" action="/user/${entity.name==null?'null':entity.name}" class="form-horizontal">
    <%--<input type="hidden" id="name" name="name" value="${entity.name}"/>--%>
    <div class="form-group">
        <label for="user" class="control-label col-xs-2">User:</label>
        <div class="col-xs-10">
            <textarea id="user" name="user" rows="1" class="form-control">${entity.name}</textarea>
        </div>
    </div>
    <div class="form-group">
        <label for="email" class="control-label col-xs-2">Email:</label>
        <div class="col-xs-10">
            <textarea id="email" name="email" rows="1" class="form-control">${entity.email}</textarea>
        </div>
    </div>
    <div class="form-group">
        <label for="password" class="control-label col-xs-2">Password:</label>
        <div class="col-xs-10">
            <textarea id="password" name="password" rows="1" class="form-control">${entity.password}</textarea>
        </div>
    </div>
    <div class="form-group">
        <div class="col-xs-offset-2 col-xs-10">
            <div class="check-box">
                <input type="checkbox" id="enabled" name="enabled" ${entity.enabled?"checked":""}/>
                <label for="enabled">Enabled</label>
            </div>
        </div>
    </div>
    <div class="form-group">
        <div class="col-xs-offset-2 col-xs-10">
            <input type="submit" value="Save" class="btn btn-primary">
            <input type="reset" value="Cancel" class="btn">
        </div>
    </div>
</form>
<jsp:include page="../footer.jsp"/>