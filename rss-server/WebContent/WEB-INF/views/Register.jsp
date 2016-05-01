<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register to Feed Aggregator</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
</head>
<body ng-app="myApp" class="ng-cloak">
	<div class="generic-container" ng-controller="UserController as ctrl">

		<div class="col-md-6 col-md-offset-3">
			<h2>Register</h2>
			<form name="form" ng-submit="ctrl.register()" role="form">
				<div class="form-group"
					ng-class="{ 'has-error': form.username.$dirty && form.username.$error.required }">
					<label for="username">Username</label> <input type="text"
						name="username" id="username" class="form-control"
						ng-model="ctrl.user.login" required /> <span
						ng-show="form.username.$dirty && form.username.$error.required"
						class="help-block">Username is required</span>
				</div>
				<div class="form-group"
					ng-class="{ 'has-error': form.password.$dirty && form.password.$error.required }">
					<label for="password">Password</label> <input type="password"
						name="password" id="password" class="form-control"
						ng-model="ctrl.user.password" required /> <span
						ng-show="form.password.$dirty && form.password.$error.required"
						class="help-block">Password is required</span>
				</div>
				<div class="form-actions">
					<button type="submit" ng-disabled="form.$invalid || vm.dataLoading"
						class="btn btn-primary">Register</button>
					<a href="rss" class="btn btn-link">Cancel</a>
				</div>
			</form>
		</div>
</body>


<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>
<script src="<c:url value='/app.js' />"></script>
<script src="<c:url value='/feed_service.js' />"></script>
<script src="<c:url value='/user_service.js' />"></script>
<script src="<c:url value='/feed_controller.js' />"></script>
<script src="<c:url value='/user_controller.js' />"></script>
</html>