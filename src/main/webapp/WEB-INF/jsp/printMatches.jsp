<%@ page contentType = "text/html; charset = UTF-8" language="java"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page isELIgnored = "false" %>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h1>${country}</h1>
<h3>${league}</h3>
<p><a href="/">Back</a>
<c:forEach items="${list}" var="item">
   <p>${item}</p>
</c:forEach>
</body>
</html>