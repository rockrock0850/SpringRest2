<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head> 
<meta http-equiv="Content-Type" content="width=device-width; charset=UTF-8">
<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/bootstrap-datetimepicker.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/resources/js/jquery-1.11.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/moment.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap-datetimepicker.js"></script>
<title>Bootstrap Demo</title>
</head>
<body>
<button type="button" class="btn btn-default" aria-label="Left Align">
  <span class="glyphicon glyphicon-align-left" aria-hidden="true"></span>
</button>
<button type="button" class="btn btn-default" aria-label="Center Align">
  <span class="glyphicon glyphicon-align-center" aria-hidden="true"></span>
</button>
<button type="button" class="btn btn-default" aria-label="Right Align">
  <span class="glyphicon glyphicon-align-right" aria-hidden="true"></span>
</button>
<button type="button" class="btn btn-default btn-lg">
  <span class="glyphicon glyphicon-star" aria-hidden="true"></span> Star
</button>
<button type="button" class="btn btn-default">
  <span class="glyphicon glyphicon-star" aria-hidden="true"></span> Star
</button>
<button type="button" class="btn btn-default btn-sm">
  <span class="glyphicon glyphicon-star" aria-hidden="true"></span> Star
</button>
<div class="dropdown">
	<button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true"
		aria-expanded="true">
		Dropdown <span class="caret"></span>
	</button>
	<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
    <li><a href="#">Action</a></li>
    <li><a href="#">Another action</a></li>
    <li><a href="#">Something else here</a></li>
    <li role="separator" class="divider"></li>
    <li><a href="#">Separated link</a></li>
  </ul>
</div>
<div class="dropup">
  <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
    Dropup
    <span class="caret"></span>
  </button>
  <ul class="dropdown-menu" aria-labelledby="dropdownMenu2">
    <li><a href="#">Action</a></li>
    <li><a href="#">Another action</a></li>
    <li><a href="#">Something else here</a></li>
    <li role="separator" class="divider"></li>
    <li><a href="#">Separated link</a></li>
  </ul>
</div>
<div class="dropdown">
	<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
		<span class="glyphicon glyphicon-align-left" aria-hidden="true"></span>Login
	</button>
	<div class="dropdown-menu" style="padding: 20px 50px 20px 50px;">
		<form id="formLogin" class="form">
			<div class="form-group row">
				<label>Login</label>
			</div>
			<div class="form-group row">
				<input class="form-control" name="username" id="username" type="text" placeholder="Username" pattern="^[a-z,A-Z,0-9,_]{6,15}$" data-valid-min="6"
					title="Enter your username" required="">
			</div>
			<div class="form-group row">
				<input class="form-control" name="password" id="password" type="password" placeholder="Password" title="Enter your password" required="">
			</div>
		</form>
		<button type="button" id="btnLogin" class="btn">Login</button>
	</div>
</div>
<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="#">WebSiteName</a>
    </div>
    <ul class="nav navbar-nav">
      <li class="dropdown">
        <a class="dropdown-toggle" data-toggle="dropdown" href="#">Page 1
        <span class="caret"></span></a>
        <ul class="dropdown-menu">
          <li><a href="#">Page 1-1</a></li>
          <li><a href="#">Page 1-2</a></li>
          <li><a href="#">Page 1-3</a></li> 
        </ul>
      </li>
    </ul>
    <ul class="nav navbar-nav pull-right">
      <li><a href="#"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>
      <li class="dropdown">
      	<a class="dropdown-toggle" data-toggle="dropdown" href="#"><span class="glyphicon glyphicon-log-in"></span>Login</a>
        <ul class="dropdown-menu pull-right" style="padding: 20px 30px 5px 30px;">
          <li>
          	<form>
				<div class="form-group row">
					<label>Login</label>
				</div>
				<div class="form-group row">
					<input class="form-control" name="username" id="username" type="text" placeholder="Username" pattern="^[a-z,A-Z,0-9,_]{6,15}$" data-valid-min="6"
						title="Enter your username" required="">
				</div>
				<div class="form-group row">
					<input class="form-control" name="password" id="password" type="password" placeholder="Password" title="Enter your password" required="">
				</div>
			</form>
			<div class="form-group">
				<div class="">
					<button class="form-control" type="button" id="btnLogin" class="btn">Login</button>
				</div>
			</div>
          </li>
        </ul>
      </li>
    </ul>
  </div>
</nav>
</body>
<script type="text/javascript">
</script>
</html>