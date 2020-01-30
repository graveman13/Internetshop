package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Order;

@Dao
public class OrderDaoJdbcImpl extends AbstractDao<Order> implements OrderDao {
    private static final String TABLE_ORDERS = "internetshop.orders";
    private static final String TABLE_ORDERS_ITEMS = "internetshop.orders_items";
    private static final String TABLE_ITEMS = "internetshop.items";

    public OrderDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Order create(Order order) throws DataProcessingException {
        String create = String.format("insert into %s (user_id) values(?)", TABLE_ORDERS);
        try (PreparedStatement statement =
                     connection.prepareStatement(create, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, order.getUserId());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            while (rs.next()) {
                order.setOrderId(rs.getLong(1));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create order " + e);
        }
        addItemsToOrderItems(order);
        return order;
    }

    public Order addItemsToOrderItems(Order order) throws DataProcessingException {
        String addItems =
                String.format("insert into  %s (order_id,item_id) values (?,?)",
                        TABLE_ORDERS_ITEMS);
        try (PreparedStatement statement = connection.prepareStatement(addItems)) {
            for (Item item : order.getItems()) {
                statement.setLong(1, order.getOrderId());
                statement.setLong(2, item.getItemId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add items to order " + e);
        }
        return order;
    }

    @Override
    public Optional<Order> get(Long id) throws DataProcessingException {
        Order order = new Order();
        order.setOrderId(id);
        String get = String.format("select * from %s where orders_id =?", TABLE_ORDERS);
        try (PreparedStatement statement = connection.prepareStatement(get)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                order.setUserId(rs.getLong("user_id"));
            }
            order.setItems(getAllItemsByOrder(order));
            return Optional.of(order);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get order " + e);
        }
    }

    public List<Item> getAllItemsByOrder(Order order) throws DataProcessingException {
        List<Item> items = new ArrayList<>();
        String getAllItem = String.format("select * from %s"
                + " join %s on orders_items.item_id = items.item_id"
                + " where orders_items.order_id =?;", TABLE_ITEMS, TABLE_ORDERS_ITEMS);
        try (PreparedStatement statement = connection.prepareStatement(getAllItem)) {
            statement.setLong(1, order.getOrderId());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                item.setItemId(rs.getLong("item_id"));
                item.setItemName(rs.getString("item_name"));
                item.setItemPrice(rs.getDouble("item_price"));
                items.add(item);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all items by order " + e);
        }
        return items;
    }

    @Override
    public Order update(Order order) throws DataProcessingException {
        deleteAllItemsInOrder(order);
        return addItemsToOrderItems(order);
    }

    public boolean deleteAllItemsInOrder(Order order) throws DataProcessingException {
        String deleteAllItems = String.format(
                "delete from %s where order_id =?;", TABLE_ORDERS_ITEMS);
        try (PreparedStatement statement = connection.prepareStatement(deleteAllItems)) {
            statement.setLong(1, order.getOrderId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete all items in order " + e);
        }
        return true;
    }

    @Override
    public boolean deleteId(Long id) throws DataProcessingException {
        String deleteId = String.format("delete from %s where orders_id =?", TABLE_ORDERS);
        try (PreparedStatement statement = connection.prepareStatement(deleteId)) {
            statement.setLong(1, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delet order by order id " + e);
        }
    }

    @Override
    public boolean delete(Order order) throws DataProcessingException {
        return deleteId(order.getOrderId());
    }

    @Override
    public List<Order> getAll() throws DataProcessingException {
        List<Order> orders = new ArrayList<>();
        String getAllOrders = String.format("select * from %s", TABLE_ORDERS);
        try (PreparedStatement statement = connection.prepareStatement(getAllOrders)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Order order = get(rs.getLong("orders_id")).get();
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all orders item " + e);
        }
        return orders;
    }
}
