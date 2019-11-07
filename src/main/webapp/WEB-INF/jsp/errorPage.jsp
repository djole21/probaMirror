<%@ page contentType = "text/html; charset = UTF-8" language="java"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page isELIgnored = "false" %>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<%@ page session="false"%>
<html>
<head>
    <title>Home</title>
</head>
<body>
    <a href="/">Home</a>

    <h1>There is an error. <a href="/app/">Home</a></h1>
    <p>status: ${status}</p>
    <p>reason: ${reason}</p>
    <p>${msg}</p>
</body>
</html>