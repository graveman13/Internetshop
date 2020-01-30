package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mate.academy.internetshop.exceptions.AuthenticatioException;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;

public class LoginController extends HttpServlet {
    @Inject
    private static UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String login = req.getParameter("login");
        String psw = req.getParameter("psw");
        try {
            User user = userService.login(login, psw);

            HttpSession session = req.getSession(true);
            session.setAttribute("userId", user.getUserId());
            Cookie cookie = new Cookie("MATE", user.getToken());

            resp.addCookie(cookie);
            req.setAttribute("userName", user.getName());
            req.getRequestDispatcher("WEB-INF/views/menu.jsp").forward(req, resp);

        } catch (AuthenticatioException | DataProcessingException e) {
            req.setAttribute("error", "Incorrect login and password");
            req.getRequestDispatcher("WEB-INF/views/login.jsp").forward(req, resp);
        }
    }
}
