<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="users" scope="request" type="java.util.List<mate.academy.internetshop.model.User>"/>
<%@ page contentType="text/html;charset=UTF-16" language="java" %>
<html>
<head>
    <title>All User</title>
</head>
<body>
<H3>All users list:</H3>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Login</th>
        <th>Name</th>
        <th>Surname</th>
        <th>Delete</th>
    </tr>
    <c:forEach var="user" items="${users}">
        <tr>
            <td>
                <c:out value="${user.userId}"/>
            </td>
            <td>
                <c:out value="${user.login}"/>
            </td>
            <td>
                <c:out value="${user.name}"/>
            </td>
            <td>
                <c:out value="${user.surname}"/>
            </td>
            <td>
                <a href="/internet_shop_war_exploded/servlet/delete_user?user_id=${user.userId}">DELETE</a>
            </td>
        </tr>
    </c:forEach>
</table>
<a href="/internet_shop_war_exploded">BACK TO HOME</a>
</body>
</html>
