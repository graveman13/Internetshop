package mate.academy.internetshop.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;
import org.apache.log4j.Logger;

public class GetAllUsersController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(GetAllUsersController.class);
    @Inject
    private static UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<User> users = null;
        try {
            users = userService.getAll();
        } catch (DataProcessingException e) {
            LOGGER.error(e);
            req.setAttribute("message", e);
            req.getRequestDispatcher("/WEB-INF/views/dataProcessingExeption.jsp");
        }
        req.setAttribute("users", users);
        req.getRequestDispatcher("/WEB-INF/views/allUsers.jsp").forward(req, resp);
    }
}
