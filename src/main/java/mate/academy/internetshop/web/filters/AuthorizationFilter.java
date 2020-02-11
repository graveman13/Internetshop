package mate.academy.internetshop.web.filters;

import static mate.academy.internetshop.model.Role.RoleName.ADMIN;
import static mate.academy.internetshop.model.Role.RoleName.USER;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;
import org.apache.log4j.Logger;

public class AuthorizationFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(AuthorizationFilter.class);
    @Inject
    private static UserService userService;
    private Map<String, Role.RoleName> protectedUrls;

    @Override
    public void init(FilterConfig filterConfig) {
        protectedUrls = new HashMap<>();
        protectedUrls.put("/servlet/getAllUsers", ADMIN);
        protectedUrls.put("/servlet/add_item", ADMIN);
        protectedUrls.put("/servlet/bucket", USER);
        protectedUrls.put("/servlet/bucket_del_item", USER);
        protectedUrls.put("/servlet/bucket_add_item", USER);
        protectedUrls.put("/servlet/order", USER);
        protectedUrls.put("/servlet/orders", USER);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String requestedUrl = req.getRequestURI().replace(req.getContextPath(), "");
        Role.RoleName roleName = protectedUrls.get(requestedUrl);
        if (roleName == null) {
            processAuthentication(req, resp, chain);
            return;
        }
        Long userId = (Long) req.getSession().getAttribute("userId");
        try {
            User user = userService.get(userId);
            if (verifyRole(user, roleName)) {
                processAuthentication(req, resp, chain);
            } else {
                processDenied(req, resp);
            }
        } catch (DataProcessingException e) {
            LOGGER.error(e);
            throw new RuntimeException();
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

    @Override
    public void destroy() {
    }
}
