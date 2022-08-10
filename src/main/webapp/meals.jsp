<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <title>Meals</title>
    <link rel="stylesheet" href="./css/style.css">
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table border="0" cellpadding="5" cellspacing="1">
    <tr>
        <td colspan="5"><a href="?action=add">create new meal</a></td>
    </tr>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th colspan="2">Action</th>
    </tr>
    <c:forEach items="${mealsList}" var="meal">
        <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.model.MealTo"/>
        <tr class="${meal.excess ? 'warn' : 'norm'}">
            <td>${TimeUtil.toString(meal.dateTime)}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="?id=${meal.id}&action=edit">edit</a></td>
            <td><a href="?id=${meal.id}&action=delete">delete</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
