package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.UserService;

public class RegistrationController extends HttpServlet {
    @Inject
    private static UserService userService;
    @Inject
    private static BucketService bucketService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/views/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = new User();
        user.setLogin(req.getParameter("login"));
        user.setPassword(req.getParameter("psw"));
        user.setName(req.getParameter("user_name"));
        user.setSurname(req.getParameter("user_surname"));
        User userWithID = null;
        try {
            userWithID = userService.create(user);

            HttpSession session = req.getSession(true);
            session.setAttribute("userId", userWithID.getUserId());
            Cookie cookie = new Cookie("MATE", userWithID.getToken());
            resp.addCookie(cookie);

            Bucket bucket = new Bucket();
            bucket.setUser(userWithID.getUserId());
            bucketService.create(bucket);
        } catch (DataProcessingException e) {
            e.printStackTrace();
        }
        resp.sendRedirect(req.getContextPath() + "/servlet/getAllUsers");
    }
}
