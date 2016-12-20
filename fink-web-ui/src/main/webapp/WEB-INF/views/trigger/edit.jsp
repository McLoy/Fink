<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="../header.jsp"/>
<form method="post" action="/job/${entity.job.key}/trigger/${entity.key == null ? 'null' : entity.key}" class="form-horizontal">
    <input type="hidden" id="key" name="key" value="${entity.key}"/>
    <input type="hidden" id="jobKey" name="jobKey" value="${entity.jobKey}"/>
    <div class="form-group">
        <label class="col-sm-2 control-label">Job:</label>
        <div class="col-sm-10">
            <p class="form-control-static"><a href="/job/${entity.job.key}?form">${entity.job.description}</a></p>
        </div>
    </div>
    <div class="form-group">
        <label for="description" class="control-label col-xs-2">Description:</label>
        <div class="col-xs-10">
            <textarea id="description" name="description" rows="1" class="form-control">${entity.description}</textarea>
        </div>
    </div>
    <div class="form-group">
        <label for="calendarName" class="control-label col-xs-2">Calendar:</label>
        <div class="col-xs-10">
            <select id="calendarName" name="calendarName" class="form-control">
                <c:forEach var="calendarName" items="${calendarNames}">
                    <option value="${calendarName}" ${calendarName.equals(entity.calendarName)?"selected":""}>${calendarName}</option>
                </c:forEach>
            </select>
        </div>
    </div>
    <div class="form-group">
        <label for="priority" class="control-label col-xs-2">Priority:</label>
        <div class="col-xs-10">
            <select id="priority" name="priority" class="form-control">
                <c:forEach var="priority" items="${priorities}">
                    <option value="${priority.value}" ${priority.value == entity.priority?"selected":""}>${priority.representation}</option>
                </c:forEach>
            </select>
        </div>
    </div>
    <div class="form-group">
        <label for="startTime" class="control-label col-xs-2">Start:</label>
        <div class="col-xs-10">
            <div class='input-group date' id="start-datetime-picker">
                <input type='text' id="startTime" name="startTime" class="form-control" placeholder="dd.MM.yyyy HH:mm:ss"
                       value="<fmt:formatDate pattern="dd.MM.yyyy HH:mm:ss" value="${entity.startTime}"/>"/>
                <span class="input-group-addon">
                    <span class="glyphicon glyphicon-calendar"></span>
                </span>
            </div>
        </div>
    </div>
    <div class="form-group">
        <label for="endTime" class="control-label col-xs-2">End:</label>
        <div class="col-xs-10">
            <div class='input-group date' id='end-datetime-picker'>
                <input type='text' id="endTime" name="endTime" class="form-control" placeholder="dd.MM.yyyy HH:mm:ss"
                       value="<fmt:formatDate pattern="dd.MM.yyyy HH:mm:ss" value="${entity.endTime}"/>"/>
                <span class="input-group-addon">
                    <span class="glyphicon glyphicon-calendar"></span>
            </span>
            </div>
        </div>
    </div>
    <script type="text/javascript">
        $(function () {
            $('#start-datetime-picker').datetimepicker({
//                useCurrent: false, //Important! See issue #1075
                format: 'dd.mm.yyyy hh:ii:ss',
                todayHighlight: true,
                autoclose: true,
                pickDate: true,
                pickTime: true,
                pickSeconds: true,
                sideBySide: true
            });
            $('#end-datetime-picker').datetimepicker({
                useCurrent: false, //Important! See issue #1075
                format: 'dd.mm.yyyy hh:ii:ss',
                todayHighlight: true,
                autoclose: true,
                pickDate: true,
                pickTime: true,
                pickSeconds: true,
                sideBySide: true
            });
            $("#start-datetime-picker").on("dp.change", function (e) {
                $('#end-datetime-picker').data("DateTimePicker").minDate(e.date);
            });
            $("#end-datetime-picker").on("dp.change", function (e) {
                $('#start-datetime-picker').data("DateTimePicker").maxDate(e.date);
            });
        });
    </script>
    <div class="form-group">
        <label for="misfireInstruction" class="control-label col-xs-2">Misfire instruction:</label>
        <div class="col-xs-10">
            <select id="misfireInstruction" name="misfireInstruction" class="form-control">
                <c:forEach var="misfireInstruction" items="${misfireInstructions}">
                    <option value="${misfireInstruction.value}" ${misfireInstruction.value == entity.misfireInstruction?"selected":""}>${misfireInstruction.representation}</option>
                </c:forEach>
            </select>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">Previous fire time:</label>
        <div class="col-sm-10">
            <p class="form-control-static"><fmt:formatDate pattern="dd.MM.yyyy HH:mm:ss" value="${entity.previousFireTime}"/></p>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">Next fire time:</label>
        <div class="col-sm-10">
            <p class="form-control-static"><fmt:formatDate pattern="dd.MM.yyyy HH:mm:ss" value="${entity.nextFireTime}"/></p>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">Final fire time:</label>
        <div class="col-sm-10">
            <p class="form-control-static"><fmt:formatDate pattern="dd.MM.yyyy HH:mm:ss" value="${entity.finalFireTime}"/></p>
        </div>
    </div>
    <div class="form-group">
        <label for="interval" class="control-label col-xs-2">Interval:</label>
        <div class="col-xs-10">
            <input type="number" id="interval" name="interval" class="form-control" value="${entity.interval}">
        </div>
    </div>
    <div class="form-group">
        <label for="misfireInstruction" class="control-label col-xs-2">Time unit:</label>
        <div class="col-xs-10">
            <select id="timeUnit" name="timeUnit" class="form-control">
                <c:forEach var="timeUnit" items="${timeUnits}">
                    <option value="${timeUnit}" ${timeUnit == entity.timeUnit?"selected":""}>${timeUnit}</option>
                </c:forEach>
            </select>
        </div>
    </div>
    <div class="form-group">
        <label for="repeatCount" class="control-label col-xs-2">Repeat count:</label>
        <div class="col-xs-10">
            <input type="number" id="repeatCount" name="repeatCount" class="form-control" value="${entity.repeatCount}">
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