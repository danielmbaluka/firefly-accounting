<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Firefly | Login</title>

    <!-- Page title set in pageTitle directive -->
    <title page-title></title>
    <!-- Bootstrap and Fonts -->
    <link href="<c:url value="/resources/css/inspinia/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/fonts/inspinia/font-awesome/css/font-awesome.css" />" rel="stylesheet">

    <!-- Main Inspinia CSS files -->
    <link href="<c:url value="/resources/css/inspinia/animate.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/inspinia/style.css" />" rel="stylesheet">


</head>
<body class="gray-bg">
<div class="middle-box text-center loginscreen animated fadeInDown">
    <div>
        <div>

            <img src="/resources/images/main/firefly1.png">

        </div>
        <h3>Welcome to Company Name</h3>
        <p>Accounting and Inventory System.
            <!--Continually expanded and constantly improved Inspinia Admin Them (IN+)-->
        </p>

        <form method="post" action="${pageContext.request.contextPath}/login">
            <fieldset>
                <c:if test="${param.error != null}">
                    <div class="alert alert-danger">
                        Invalid username and password.
                    </div>
                </c:if>
                <c:if test="${param.logout != null}">
                    <div class="alert alert-info">
                        You have been logged out.
                    </div>
                </c:if>

                <div class="form-group">
                    <input type="text" class="form-control"  required="" id="username" name="username" placeholder="Username" />
                </div>
                <div class="form-group">
                    <input type="password" class="form-control"  required="" id="password" name="password" placeholder="Password" />
                </div>
                <button type="submit" class="btn btn-primary block full-width m-b">Login</button>
            </fieldset>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

        </form>
        <p class="m-t"> <small><strong>Copyright</strong> Company Name &copy; 2014</small> </p>
    </div>
</div>
</body>
</html>