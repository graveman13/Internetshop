package mate.academy.internetshop.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Service;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
    @Inject
    private static OrderDao orderDao;
    @Inject
    private static UserDao userDao;

    @Override
    public Order create(Order entity) {
        return orderDao.create(entity);
    }

    @Override
    public Order get(Long id) {
        return orderDao.get(id).get();
    }

    @Override
    public Order update(Order item) {
        return orderDao.update(item);
    }

    @Override
    public boolean deleteId(Long id) {
        return orderDao.deleteId(id);
    }

    @Override
    public boolean delete(Order order) {
        return orderDao.deleteId(order.getOrderId());
    }

    @Override
    public List<Order> getAll() {
        return orderDao.getAll();
    }

    @Override
    public Order completeOrder(List<Item> items, User user) {
        Order order = new Order();
        order.setUserId(user.getUserId());
        order.setItems(items);
        return orderDao.create(order);
    }

    @Override
    public List<Order> getUserOrders(User user) {
        return orderDao.getAll().stream()
                .filter(order -> order.getUserId().equals(user.getUserId()))
                .collect(Collectors.toList());
    }
}
