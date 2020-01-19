package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;

public class InjectDataController extends HttpServlet {
    @Inject
    private static UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = new User();
        user.setSurname("Bob");
        user.setSurname("Martin");
        user.addRoles(Role.of("USER"));
        user.setLogin("bob");
        user.setPassword("1");
        userService.create(user);
        req.setAttribute("userName", user.getName());

        User admin = new User();
        admin.setSurname("Admin");
        admin.setSurname("Admin");
        admin.addRoles(Role.of("ADMIN"));
        admin.setLogin("admin");
        admin.setPassword("1");
        userService.create(admin);
        req.setAttribute("userName", admin.getName());
        req.getRequestDispatcher("WEB-INF/views/menu.jsp").forward(req, resp);
    }
}
