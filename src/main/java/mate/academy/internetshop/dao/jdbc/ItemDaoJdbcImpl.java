package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Item;

@Dao
public class ItemDaoJdbcImpl extends AbstractDao<Item> implements ItemDao {
    private static final String DATA_BASE_NAME = "internetshop.items";

    public ItemDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Item create(Item item) throws DataProcessingException {
        String query = String.format("INSERT INTO %s(item_name, item_price) VALUES (?, ?);",
                DATA_BASE_NAME);
        try (PreparedStatement statement =
                     connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, item.getItemName());
            statement.setDouble(2, item.getItemPrice());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            while (rs.next()) {
                item.setItemId(rs.getLong(1));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create item " + e);
        }
        return item;
    }

    @Override
    public Optional<Item> get(Long id) throws DataProcessingException {
        String query = String.format("SELECT * FROM %s WHERE item_id=?;",
                DATA_BASE_NAME);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                return Optional.of(setItemsData(rs));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get item " + e);
        }
        return Optional.empty();
    }

    @Override
    public List<Item> getAll() throws DataProcessingException {
        List<Item> items = new ArrayList<>();
        String query = String.format("SELECT * FROM %s;",
                DATA_BASE_NAME);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                items.add(setItemsData(rs));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all items " + e);
        }
        return items;
    }

    private Item setItemsData(ResultSet rs) throws DataProcessingException {
        Item item = new Item();
        try {
            Long itemId = rs.getLong("item_id");
            String itemName = rs.getString("item_name");
            Double itemPrice = rs.getDouble("item_price");
            item.setItemId(itemId);
            item.setItemName(itemName);
            item.setItemPrice(itemPrice);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't set items " + e);
        }
        return item;
    }

    @Override
    public Item update(Item item) throws DataProcessingException {
        String query = String.format(Locale.ROOT,
                "UPDATE FROM %s SET item_id=?, item_name=?, item_price=?;",
                DATA_BASE_NAME);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, item.getItemId());
            statement.setString(2, item.getItemName());
            statement.setDouble(3, item.getItemPrice());
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update item " + e);
        }
        return item;
    }

    @Override
    public boolean deleteId(Long id) throws DataProcessingException {
        String query = String.format("DELETE FROM %s WHERE item_id=?;",
                DATA_BASE_NAME);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            statement.executeQuery(query);
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete item " + e);
        }
    }

    @Override
    public boolean delete(Item item) throws DataProcessingException {
        return deleteId(item.getItemId());
    }
}
