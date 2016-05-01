<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Feed Aggregator</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
</head>
<body ng-app="myApp" class="ng-cloak">
	<div class="generic-container" ng-controller="FeedController as ctrl">

		<div class="panel panel-default">
			<!-- Default panel contents -->
			<div class="panel-heading">
				<span class="lead">List of Feeds </span>
			</div>
			<div class="tablecontainer">
				<table class="table table-hover">
					<thead>
						<tr>
							<th>Title</th>
							<th>URL</th>
							<th width="20%"></th>
						</tr>
					</thead>
					<tr ng-repeat="f in ctrl.feeds">
						<td>{{f.title}}</td>
						<td><a href="{{f.url}}" target="_blank">{{f.url}}</a></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>


<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>
<script src="<c:url value='/app.js' />"></script>
<script src="<c:url value='/feed_service.js' />"></script>
<script src="<c:url value='/user_service.js' />"></script>
<script src="<c:url value='/feed_controller.js' />"></script>
</html>