package mate.academy.internetshop.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.service.ItemService;
import mate.academy.internetshop.service.UserService;
import org.apache.log4j.Logger;

public class AddItemController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(AddItemController.class);
    @Inject
    private static ItemService itemService;
    @Inject
    private static UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getParameter("userName");
        req.getRequestDispatcher("/WEB-INF/views/addItems.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Item item = new Item();
        item.setItemName(req.getParameter("item_name"));
        item.setItemPrice(Double.valueOf(req.getParameter("item_price")));
        try {
            itemService.create(item);
        } catch (DataProcessingException e) {
            LOGGER.error(e);
            req.setAttribute("message", e);
            req.getRequestDispatcher("/WEB-INF/views/dataProcessingExeption.jsp")
                    .forward(req, resp);
        }
        req.setAttribute("itemName", item.getItemName());
        req.setAttribute("itemPrice", item.getItemPrice());
        getServletContext().getRequestDispatcher("/WEB-INF/views/addedItem.jsp")
                .forward(req, resp);
    }
}
