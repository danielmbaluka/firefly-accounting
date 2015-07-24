<%-- 
    Document   : admin
    Created on : 06 26, 14, 11:55:53 AM
    Author     : TSI Admin


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error</title>
    </head>
    <body>
        <h1>Page not found</h1>
    </body>
</html>--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>INSPINIA | 404 Error</title>

    <!-- Bootstrap and Fonts -->
    <link href="<c:url value="/resources/css/inspinia/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/fonts/inspinia/font-awesome/css/font-awesome.css" />" rel="stylesheet">

    <!-- Main Inspinia CSS files -->
    <link href="<c:url value="/resources/css/inspinia/animate.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/inspinia/style.css" />" rel="stylesheet">

</head>

<body class="gray-bg">


<div class="middle-box text-center animated fadeInDown">
    <h1>404</h1>
    <h3 class="font-bold">Page Not Found</h3>

    <div class="error-desc">
        Sorry, but the page you are looking for has note been found. Try checking the URL for error, then hit the refresh button on your browser or try found something else in our app.
        <form class="form-inline m-t" role="form">
            <div class="form-group">
                <input type="text" class="form-control" placeholder="Search for page">
            </div>
            <button type="submit" class="btn btn-primary">Search</button>
        </form>
    </div>
</div>

</body>

</html>
