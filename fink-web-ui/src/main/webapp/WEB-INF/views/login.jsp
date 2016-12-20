<jsp:include page="header.jsp"/>
<div class="col-md-offset-4 col-md-4">
    <div class="panel panel-default">
        <div class="panel-body">
            <form role="form" action="/login" method="post">
                <div class="form-group">
                    <label for="exampleInputEmail1">Username or Email</label>
                    <input type="text" class="form-control" id="exampleInputEmail1" placeholder="Enter email" name='username'>
                </div>
                <div class="form-group">
                    <label for="exampleInputPassword1">Password <a href="#">(forgot password)</a></label>
                    <input type="password" class="form-control" id="exampleInputPassword1" placeholder="Password" name='password'>
                </div>
                <button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-log-in"></span> Login</button>
            </form>
        </div>
    </div>
<jsp:include page="footer.jsp"/>