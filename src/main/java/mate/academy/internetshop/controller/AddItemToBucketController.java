package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.ItemService;

public class AddItemToBucketController extends HttpServlet {
    @Inject
    private static ItemService itemService;
    @Inject
    private static BucketService bucketService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String itemId = req.getParameter("item_id");
        try {
            Item item = itemService.get(Long.valueOf(itemId));
            Long userId = (Long) req.getSession(true).getAttribute("userId");
            Bucket bucket = bucketService.getBucket(userId);
            bucketService.addItem(bucket, item);
        } catch (DataProcessingException e) {
            req.setAttribute("message",e);
            req.getRequestDispatcher("/WEB-INF/views/dataProcessingExeption.jsp");
        }
        resp.sendRedirect(req.getContextPath() + "/servlet/bucket");
    }
}
