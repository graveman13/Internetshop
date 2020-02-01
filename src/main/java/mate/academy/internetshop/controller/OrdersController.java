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
import org.apache.log4j.Logger;

public class OrdersController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(OrdersController.class);
    @Inject
    private static OrderService orderService;
    @Inject
    private static UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long userId = (Long) req.getSession(true).getAttribute("userId");
        try {
            User user = userService.get(userId);
            req.setAttribute("orders", orderService.getUserOrders(user));
        } catch (DataProcessingException e) {
            LOGGER.error(e);
            req.setAttribute("message",e);
            req.getRequestDispatcher("/WEB-INF/views/dataProcessingExeption.jsp");
        }
        req.getRequestDispatcher("/WEB-INF/views/order.jsp").forward(req, resp);
    }
}
