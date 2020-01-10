package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Order;

@Dao
public class OrderDaoImpl implements OrderDao {
    private static Long orderId = 0L;

    @Override
    public Order create(Order order) {
        order.setOrderId(orderId++);
        Storage.orders.add(order);
        return order;
    }

    @Override
    public Optional<Order> get(Long id) {
        return Optional.ofNullable(Storage.orders.stream()
                .filter(order -> order.getOrderId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Can't find order with id " + id)));
    }

    @Override
    public Order update(Order order) {
        Order updateOrder = Storage.orders.stream()
                .filter(orderTmp -> orderTmp.getOrderId().equals(order.getOrderId()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Can't find order"));
        updateOrder.getItems().clear();
        order.setItems(order.getItems());
        return updateOrder;
    }

    @Override
    public boolean deleteId(Long id) {
        return Storage.orders.removeIf(order -> order.getOrderId().equals(id));
    }

    @Override
    public boolean delete(Order order) {
        return deleteId(order.getOrderId());
    }

    @Override
    public List<Order> getAll() {
        return Storage.orders;
    }
}
