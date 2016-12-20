<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="../header.jsp"/>
<form method="post" action="/job/${entity.key==null?'null':entity.key}" class="form-horizontal">
    <input type="hidden" id="key" name="key" value="${entity.key}"/>
    <div class="form-group">
        <label for="jobClass" class="control-label col-xs-2">Job class:</label>
        <div class="col-xs-10">
            <select id="jobClass" name="jobClass" onchange="fillJobDescription(this)" class="form-control">
                <c:forEach var="clazz" items="${jobClasses}">
                    <option value="${clazz.name}" ${clazz == entity.jobClass?"selected":""}>${clazz.simpleName}</option>
                </c:forEach>
            </select>
        </div>
    </div>
    <div class="form-group">
        <label for="description" class="control-label col-xs-2">Description:</label>
        <div class="col-xs-10">
            <textarea id="description" name="description" rows="1" class="form-control">${entity.description}</textarea>
        </div>
    </div>
    <div class="form-group">
        <div class="col-xs-offset-2 col-xs-10">
            <div class="check-box">
                <input type="checkbox" id="durable" name="durable" ${entity.durable?"checked":""}/>
                <label for="durable">Durable:</label>
            </div>
        </div>
    </div>
    <div class="form-group">
        <div class="col-xs-offset-2 col-xs-10">
            <div class="check-box">
                <input type="checkbox" id="requestsRecovery" name="requestsRecovery" ${entity.requestsRecovery?"checked":""}/>
                <label for="requestsRecovery">Requests recovery:</label>
            </div>
        </div>
    </div>
    <div class="form-group">
        <div class="col-xs-offset-2 col-xs-10">
            <input type="submit" value="Save" class="btn btn-primary">
            <input type="reset" value="Cancel" class="btn">
        </div>
    </div>
    <div class="col-xs-offset-2 col-xs-10">
        <ul class="nav nav-tabs nav-justified">
            <li class="active"><a data-toggle="tab" href="#jobData">Job data</a></li>
            <li><a data-toggle="tab" href="#triggrers">Triggers</a></li>
        </ul>
        <div class="tab-content">
        <div id="jobData" class="tab-pane fade in active">
            <h3>Job data:</h3>
            <c:forEach var="entry" items="${entity.jobDataMap}">
                <c:set var="key" value="${entry.key}"/>
                <c:set var="value" value="${entry.value}"/>
                <c:set var="inputId" value="jobDataMap-${key}"/>
                <div class="form-group">
                    <label for="${inputId}" class="control-label col-xs-2">${key}:</label>
                    <div class="col-xs-10">
                        <input id="${inputId}" name="jobDataMap['${key}']" class="form-control" value="${value}">
                    </div>
                </div>
            </c:forEach>
        </div>
        <div id="triggrers" class="tab-pane fade">
            <h3>Triggers:</h3>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>Description</th>
                    <th>Start</th>
                    <th>End</th>
                    <th>Priority</th>
                    <th>Next fire time</th>
                    <th>Repeat count</th>
                    <th>Interval</th>
                    <th>&nbsp;</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="trigger" items="${entity.triggers}">
                    <tr class="entity-row" onclick="editItem('job/${entity.key}/trigger','${trigger.key}')">
                        <td>${trigger.description}</td>
                        <td><fmt:formatDate pattern="dd.MM.yyyy HH:mm:ss" value="${trigger.startTime}"/></td>
                        <td><fmt:formatDate pattern="dd.MM.yyyy HH:mm:ss" value="${trigger.endTime}"/></td>
                        <td>${trigger.priority}</td>
                        <td><fmt:formatDate pattern="dd.MM.yyyy HH:mm:ss" value="${trigger.nextFireTime}"/></td>
                        <td>${trigger.repeatCount}</td>
                        <td>${trigger.repeatInterval}</td>
                        <td>
                            <button class="btn btn-danger" onclick="deleteItem('job/${entity.key}/trigger','${trigger.key}')">Delete</button>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <a class="btn btn-success" href='/job/${entity.key}/trigger?form' target="_self">Add</a>
        </div>
    </div>
    </div>
</form>
<jsp:include page="../footer.jsp"/>