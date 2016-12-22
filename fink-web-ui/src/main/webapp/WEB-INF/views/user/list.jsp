<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../header.jsp"/>
<h2>User list</h2>
<table class="table table-hover">
    <thead>
    <tr>
        <th>User</th>
        <th>Email</th>
        <th>Enabled</th>
        <th>&nbsp;</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${userList}">
        <tr class="entity-row" onclick="editItem('user','${user.name}')">
            <td>${user.name}</td>
            <td>${user.email}</td>
            <td>
                <c:if test="${user.enabled}">
                    <span class="glyphicon glyphicon-ok text-success"></span>
                </c:if>
            </td>
            <td>
                <button class="btn btn-danger" onclick="deleteItem('user','${user.id}')">Delete</button>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<button class="btn btn-success" onclick="editItem('user','')">Add</button>
<jsp:include page="../footer.jsp"/>