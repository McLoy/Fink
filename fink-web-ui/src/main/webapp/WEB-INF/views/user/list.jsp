<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../header.jsp"/>
<h2>User list</h2>
<table class="table table-hover">
    <thead>
    <tr>
        <th>Key</th>
        <th>Description</th>
        <th>Job class</th>
        <th>Durable</th>
        <th>Should recover</th>
        <th>Triggers</th>
        <th>&nbsp;</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${jobList}">
        <tr class="entity-row" onclick="editItem('user','${job.key}')">
            <td>${job.key}</td>
            <td>${job.description}</td>
            <td>${job.jobClass.simpleName}</td>
            <td>
                <c:if test="${job.durable}">
                    <span class="glyphicon glyphicon-ok text-success"></span>
                </c:if>
            </td>
            <td>
                <c:if test="${job.requestsRecovery}">
                    <span class="glyphicon glyphicon-ok text-success"></span>
                </c:if>
            </td>
            <td>${job.triggers.size()}</td>
            <td>
                <button class="btn btn-danger" onclick="deleteItem('job','${job.key}')">Delete</button>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<button class="btn btn-success" onclick="editItem('user','')">Add</button>
<jsp:include page="../footer.jsp"/>