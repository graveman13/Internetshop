<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Menu</title>
</head>
${userName}
<h3>MAIN MENU</h3>
<p>
<form action="/internet_shop_war_exploded/registration" method=get>
    <button type="submit" class="text">Register</button>
</form>
</p>
<p>
<form action="/internet_shop_war_exploded/servlet/add_item" method=get>
    <button type="submit" class="text">Add item</button>
</form>
</p>
<p>
<p>
<form action="/internet_shop_war_exploded/servlet/show_all_items" method=get>
    <button type="submit" class="text">Show all items</button>
</form>
</p>
<p>
<form action="/internet_shop_war_exploded/servlet/getAllUsers" method=get>
    <button type="submit" class="text">Show all users</button>
</form>
</p>
<p>
<form action="/internet_shop_war_exploded/servlet/bucket" method=get>
    <button type="submit" class="text">Show bucket</button>
</form>
</p>
<p>
<form action="/internet_shop_war_exploded/servlet/orders" method=get>
    <button type="submit" class="text">Show all orders</button>
</form>
</p>
<p>
<form action="/internet_shop_war_exploded/login" method=get>
    <button type="submit" class="text">Sign in</button>
</form>
</p>
<p>
<form action="/internet_shop_war_exploded/logout" method=get>
    <button type="submit" class="text">Logout</button>
</form>
</p>
</body>
</html>
