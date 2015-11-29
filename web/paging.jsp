<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
    <table border="1" cellpadding="5">
        <thead>
            <tr>
                <td><form action="paging"><input type="submit" name="surname" value="surname" /></form></td>
                <td><form action="paging"><input type="submit" name="name" value="name" /></form></td>
                <td><form action="paging"><input type="submit" name="login" value="login"></form></td>
                <td><form action="paging"><input type="submit" name="email" value="email"></form></td>
                <td><form action="paging"><input type="submit" name="phone_number" value="phone number"></form></td>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="columns" items="${rows}">
            <tr>
                <td>${columns.surname}</td>
                <td>${columns.name}</td>
                <td>${columns.login}</td>
                <td>${columns.email}</td>
                <td>${columns.phoneNumber}</td>
            </tr>
            </c:forEach>

        </tbody>
    </table>
    <label>Страница: ${pages}</label>
    <p><form action="paging">
        <input type="submit" name="remove_all" value="Очистить телефонную книгу" /><br />
        <label>Введите фамилию контакта, который хотите удалить: </label><input type="text" name="surname" />
        <input type="submit" name="remove_contact" value="Удалить контакт" />
    </form></p>
    <p><a href="index.jsp">В главное меню</a></p>
</body>
</html>
