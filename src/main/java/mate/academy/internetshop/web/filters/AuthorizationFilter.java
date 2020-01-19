package mate.academy.internetshop.web.filters;

import static mate.academy.internetshop.model.Role.RoleName.ADMIN;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;

public class AuthorizationFilter implements Filter {
    @Inject
    private static UserService userService;
    private Map<String, Role.RoleName> protectedUrls;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        protectedUrls = new HashMap<>();
        protectedUrls.put("/servlet/getAllUsers", ADMIN);
        protectedUrls.put("/servlet/add_item", ADMIN);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        Cookie[] cookies = req.getCookies();
        if (cookies == null) {
            processUnAuthentication(req, resp);
            return;
        }
        String requestedUrl = req.getRequestURI().replace(req.getContextPath(), "");
        Role.RoleName roleName = protectedUrls.get(requestedUrl);
        if (roleName == null) {
            processAuthentication(req, resp, chain);
            return;
        }
        String token = null;
        for (Cookie cookie : req.getCookies()) {
            if (cookie.getName().equals("MATE")) {
                token = cookie.getValue();
                break;
            }
        }
        if (token == null) {
            processAuthentication(req, resp, chain);
            return;
        } else {
            Optional<User> user = userService.getByToken(token);
            if (user.isPresent()) {
                if (verifyRole(user.get(), roleName)) {
                    processAuthentication(req, resp, chain);
                    return;
                } else {
                    processDenied(req, resp);
                    return;
                }
            } else {
                processAuthentication(req, resp, chain);
                return;
            }
        }
    }

    private boolean verifyRole(User user, Role.RoleName roleName) {
        return user.getRoles().stream()
                .anyMatch(r -> r.getRoleName().equals(roleName));
    }

    private void processDenied(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/accessDenied.jsp").forward(req, resp);
    }

    private void processAuthentication(HttpServletRequest req, HttpServletResponse resp,
                                       FilterChain chain) throws IOException, ServletException {
        chain.doFilter(req, resp);
    }

    private void processUnAuthentication(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.sendRedirect(req.getContextPath() + "/login");
    }

    @Override
    public void destroy() {

    }
}
