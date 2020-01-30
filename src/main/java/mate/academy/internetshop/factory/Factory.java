package mate.academy.internetshop.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.dao.jdbc.BucketDaoJdbcImpl;
import mate.academy.internetshop.dao.jdbc.ItemDaoJdbcImpl;
import mate.academy.internetshop.dao.jdbc.OrderDaoJdbcImpl;
import mate.academy.internetshop.dao.jdbc.UserDaoJdbcImpl;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.ItemService;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.UserService;
import mate.academy.internetshop.service.impl.BucketServiceImpl;
import mate.academy.internetshop.service.impl.ItemServiceImpl;
import mate.academy.internetshop.service.impl.OrderServiceImpl;
import mate.academy.internetshop.service.impl.UserServiceImpl;

public class Factory {
    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/internetshop?"
                            + "user=root&password=root&serverTimezone=UTC");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static BucketDao bucketDao;
    private static ItemDao itemDao;
    private static OrderDao orderDao;
    private static UserDao userDao;

    private static BucketService bucketService;
    private static ItemService itemService;
    private static OrderService orderService;
    private static UserService userService;

    public static BucketDao getBucketDao() {
        if (bucketDao == null) {
            bucketDao = new BucketDaoJdbcImpl(connection);
        }
        return bucketDao;
    }

    public static ItemDao getItemDao() {
        if (itemDao == null) {
            itemDao = new ItemDaoJdbcImpl(connection);
        }
        return itemDao;
    }

    public static OrderDao getOrderDao() {
        if (orderDao == null) {
            orderDao = new OrderDaoJdbcImpl(connection);
        }
        return orderDao;
    }

    public static UserDao getUserDao() {
        if (userDao == null) {
            userDao = new UserDaoJdbcImpl(connection);
        }
        return userDao;
    }

    public static BucketService getBucketService() {
        if (bucketService == null) {
            bucketService = new BucketServiceImpl();
        }
        return bucketService;
    }

    public static ItemService getItemService() {
        if (itemService == null) {
            itemService = new ItemServiceImpl();
        }
        return itemService;
    }

    public static OrderService getOrderService() {
        if (orderService == null) {
            orderService = new OrderServiceImpl();
        }
        return orderService;
    }

    public static UserService getUserService() {
        if (userService == null) {
            userService = new UserServiceImpl();
        }
        return userService;
    }

}
