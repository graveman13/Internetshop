<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="orders" scope="request" type="java.util.List<mate.academy.internetshop.model.Order>"/>
<%@ page contentType="text/html;charset=UTF-16" language="java" %>
<html>
<head>
    <title>Order</title>
</head>
<body>
<h3>Order</h3>
<h3>${user_name}</h3>
<table border="1">
    <tr>
        <th>User</th>
        <th>Order ID</th>
        <th>Items</th>
        <th>Delete Order</th>
    </tr>
    <c:forEach var="order" items="${orders}">
        <tr>
            <td>
                <c:out value="${user_name}"/>
            </td>
            <td>
                <c:out value="${order.orderId}"/>
            </td>
            <td>
                <c:forEach var="item" items="${order.items}">
                    <c:out value="${item}"/>
                </c:forEach>
            </td>
            <td>
                <a href="/internet_shop_war_exploded/servlet/delete_order?order_id=${order.orderId}">DELETE</a>
            </td>
        </tr>
    </c:forEach>
</table>
<a href="/internet_shop_war_exploded">BACK TO HOME</a>
</body>
</html>
