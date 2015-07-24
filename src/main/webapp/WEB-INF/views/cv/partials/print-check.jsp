<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag" %>

<h1 style="visibility: ${not empty message?'visible':'hidden'}">${message}</h1>
<mytag:check></mytag:check>