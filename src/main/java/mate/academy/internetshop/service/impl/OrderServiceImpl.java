package mate.academy.internetshop.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.exceptions.DataProcessingException;
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
    public Order create(Order entity) throws DataProcessingException {
        return orderDao.create(entity);
    }

    @Override
    public Order get(Long id) throws DataProcessingException {
        return orderDao.get(id).get();
    }

    @Override
    public Order update(Order item) throws DataProcessingException {
        return orderDao.update(item);
    }

    @Override
    public boolean deleteId(Long id) throws DataProcessingException {
        return orderDao.deleteId(id);
    }

    @Override
    public boolean delete(Order order) throws DataProcessingException {
        return orderDao.deleteId(order.getOrderId());
    }

    @Override
    public List<Order> getAll() throws DataProcessingException {
        return orderDao.getAll();
    }

    @Override
    public Order completeOrder(List<Item> items, User user) throws DataProcessingException {
        Order order = new Order();
        order.setUserId(user.getUserId());
        order.setItems(items);
        return orderDao.create(order);
    }

    @Override
    public List<Order> getUserOrders(User user) throws DataProcessingException {
        return orderDao.getAll().stream()
                .filter(order -> order.getUserId().equals(user.getUserId()))
                .collect(Collectors.toList());
    }
}
