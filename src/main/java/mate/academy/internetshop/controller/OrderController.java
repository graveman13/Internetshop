package mate.academy.internetshop.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.ItemService;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.UserService;

public class OrderController extends HttpServlet {
    @Inject
    private static OrderService orderService;
    @Inject
    private static UserService userService;
    @Inject
    private static ItemService itemService;
    @Inject
    private static BucketService bucketService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long userId = (Long)req.getSession(true).getAttribute("userId");

        Bucket bucket = bucketService.getBucket(userId);
        List<Item> items = bucket.getItems();

        User user = userService.get(userId);
        orderService.completeOrder(items, user);

        List<Order> orders = orderService.getUserOrders(user);
        req.setAttribute("orders", orders);
        req.setAttribute("user_name",user.getName());
        req.getRequestDispatcher("/WEB-INF/views/order.jsp").forward(req, resp);
    }
}
