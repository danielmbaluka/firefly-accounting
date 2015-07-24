<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="<c:url value="/resources/css/bootstrap/bootstrap.min.css" />" rel="stylesheet">
<title>Login</title>
</head>
<body>
<div class="container"> 
	<div class="row-top-buffer">
        <div class="alert alert-info">
            You have been logged out.
        </div>
        <c:redirect url="/"/>
	</div>
</div>
</body>
</html>