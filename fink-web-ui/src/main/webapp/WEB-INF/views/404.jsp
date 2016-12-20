<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp"/>
<div class="row">
    <div class="col-md-12">
        <div class="error-template">
            <h1>
                Oops!</h1>
            <h2>
                404 Not Found</h2>
            <div class="error-details">
                Sorry, an error has occured, Requested page not found!
            </div>
            <%--<div class="error-actions">--%>
                <%--<a href="/" class="btn btn-primary btn-lg"><span class="glyphicon glyphicon-home"></span> Take Me Home </a>--%>
                <%--&lt;%&ndash;<a href="http://www.jquery2dotnet.com" class="btn btn-default btn-lg"><span class="glyphicon glyphicon-envelope"></span> Contact Support </a>&ndash;%&gt;--%>
            <%--</div>--%>
        </div>
    </div>
</div>
<jsp:include page="footer.jsp"/>