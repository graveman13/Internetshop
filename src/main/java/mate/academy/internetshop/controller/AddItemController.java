package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.service.ItemService;

public class AddItemController extends HttpServlet {
    @Inject
    private static ItemService itemService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/views/addItems.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Item item = new Item();
        item.setItemName(req.getParameter("item_name"));
        item.setItemPrice(Double.valueOf(req.getParameter("item_price")));
        itemService.create(item);
        req.setAttribute("itemName", item.getItemName());
        req.setAttribute("itemPrice", item.getItemPrice());
        getServletContext().getRequestDispatcher("/WEB-INF/views/addedItem.jsp").forward(req, resp);
    }
}
