package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;
import mate.academy.internetshop.util.HashUtil;
import org.apache.log4j.Logger;

public class InjectDataController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(InjectDataController.class);
    @Inject
    private static UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = new User();
        user.setSurname("User");
        user.setSurname("User");
        user.addRoles(Role.of("USER"));
        user.setLogin("user");
        user.setPassword(HashUtil.hashPassword("1", HashUtil.getSalt()));
        try {
            userService.create(user);
        } catch (DataProcessingException e) {
            LOGGER.error(e);
            req.setAttribute("message", e);
            req.getRequestDispatcher("/WEB-INF/views/dataProcessingExeption.jsp");
        }
        req.setAttribute("userName", user.getName());

        User admin = new User();
        admin.setSurname("Admin");
        admin.setSurname("Admin");
        admin.addRoles(Role.of("ADMIN"));
        admin.setLogin("admin");
        admin.setPassword(HashUtil.hashPassword("1", HashUtil.getSalt()));
        try {
            userService.create(admin);
        } catch (DataProcessingException e) {
            LOGGER.error(e);
            req.setAttribute("message", e);
            req.getRequestDispatcher("/WEB-INF/views/dataProcessingExeption.jsp");
        }
        req.setAttribute("userName", admin.getName());
        req.getRequestDispatcher("WEB-INF/views/menu.jsp").forward(req, resp);
    }
}
