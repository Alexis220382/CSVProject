<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>CSV import</title>
</head>
    <form name="sendform" action="/upload" method="post" enctype="multipart/form-data">
        <input name="description" type="hidden"><br />
        <label>Нажмите кнопку "Обзор..." и выберите файл для импорта контактов</label><br />
        <input name="file_send" type="file"><br />
        <input type="submit" value="Импортировать контакты"><br />
    </form>
    <form action="paging" method="get">
        <label>Введите желаемое количество строк на странице: </label><input type="text" name="rows_on_page" />
        <input type="submit" value="Просмотр контактов"/>
    </form>
    <label><b>При импорте ${no}обновлены контакты: </b></label><br />
    <c:forEach var="s" items="${updated}">
                <b><c:out value="${s}" /></b><br />
    </c:forEach>

</body>
</html>
