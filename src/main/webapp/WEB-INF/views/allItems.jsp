<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="items" scope="request" type="java.util.List<mate.academy.internetshop.model.Item>"/>
<%@ page contentType="text/html;charset=UTF-16" language="java" %>
<html>
<head>
    <title>Show all items</title>
</head>
<body>
<H3>All items list:</H3>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Price</th>
        <th>Add bucket</th>
    </tr>
    <c:forEach var="item" items="${items}">
        <tr>
            <td>
                <c:out value="${item.itemId}"/>
            </td>
            <td>
                <c:out value="${item.itemName}"/>
            </td>
            <td>
                <c:out value="${item.itemPrice}"/>
            </td>
            <td>
                <form action="/servlet/bucket" >
                    <a href="/internet_shop_war_exploded/servlet/bucket_add_item?item_id=${item.itemId}">ADD TO BUCKET</a>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<a href="/internet_shop_war_exploded">BACK TO HOME</a>
</body>
</html>
