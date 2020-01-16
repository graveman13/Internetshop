<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="items" scope="request" type="java.util.List<mate.academy.internetshop.model.Item>"/>

<html>
<head>
    <title>Title</title>
</head>
<body>
<H3>All items in bucket:</H3>
<table border="1">
    <tr>
        <th>Name</th>
        <th>Price</th>
        <th>DELETE</th>
    </tr>
    <c:forEach var="item" items="${items}">
        <tr>
            <td>
                <c:out value="${item.itemName}"/>
            </td>
            <td>
                <c:out value="${item.itemId}"/>
            </td>
            <td>
                <a href="/internet_shop_war_exploded/servlet/bucket_del_item?item_id=${item.itemId}">DELETE</a>
            </td>
        </tr>
    </c:forEach>
</table>
<p><a href="/internet_shop_war_exploded/servlet/show_all_items">SHOW ALL ITEMS</a></p>
<p><a href="/internet_shop_war_exploded/servlet/order">COMPLITE ORDER</a></p>
<a href="/internet_shop_war_exploded">BACK TO HOME</a>
</body>
</html>
