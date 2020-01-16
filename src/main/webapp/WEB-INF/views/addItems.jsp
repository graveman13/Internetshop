<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add item</title>
</head>
<body>
<h3>ITEM ADDING</h3>
<form action="/internet_shop_war_exploded/servlet/add_item" method=post>
    <label for="item_name"><b>Item name</b></label>
    <input type="text" name="item_name" required></p>
    <p><label for="item_price"><b>Item price</b></label>
        <input type="text" name="item_price" required></p>
    <p>
        <button type="submit" class="text">Add item</button>
    </p>
</form>
<a href="/internet_shop_war_exploded">BACK TO HOME</a>
</body>
</html>
