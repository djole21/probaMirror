<%@ page contentType = "text/html; charset = UTF-8" language="java"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Bet365</title>
</head>
<body>
<h1>Bet365</h1>
<form method="post" action="/startBet365">
    <p>Country: <input type="text" name="country"></p>
    <p>League: <input type="text" name="league"></p>
    <p>League: <input type="text" name="url"></p>

    <input type="submit" value="Try me!">
</form>
</body>
</html>