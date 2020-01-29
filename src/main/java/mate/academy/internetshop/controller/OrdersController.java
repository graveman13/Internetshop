package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.UserService;

public class OrdersController extends HttpServlet {

    @Inject
    private static OrderService orderService;
    @Inject
    private static UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long userId = (Long) req.getSession(true).getAttribute("userId");
        User user = null;
        try {
            user = userService.get(userId);
            req.setAttribute("orders", orderService.getUserOrders(user));
        } catch (DataProcessingException e) {
            e.printStackTrace();
        }
        req.getRequestDispatcher("/WEB-INF/views/order.jsp").forward(req, resp);
    }
}
