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
<h2>Meal ${param.action == "add" ? "Add" : "Edit"}</h2>

<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
<form name="mealedit" method="POST" action="">
    <input type="hidden" name="id" value="${meal.id}"/>
    <div class="formRow">
        <label for="dateTime">Дата и время</label>
        <input type="datetime-local" id="dateTime" name="dateTime" value="${TimeUtil.toString(meal.dateTime)}" required/>
    </div>
    <div class="formRow">
        <label for="description">Описание</label>
        <input type="text" id="description" name="description" value="${meal.description}" required/>
    </div>
    <div class="formRow">
        <label for="calories">Калории</label>
        <input type="number" id="calories" name="calories" value="${meal.calories}" required/>
    </div>
    <div class="formRow">
        <input type="submit" value="Save"/>
        <input type="button" value="Cancel" onclick="history.go(-1);"/>
    </div>
</form>

</body>
</html>
