package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Item;

@Dao
public class ItemDaoJdbcImpl extends AbstractDao<Item> implements ItemDao {
    private static final String DATA_BASE_NAME = "internetshop.items";

    public ItemDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Item create(Item entity) {
        String query = String.format(Locale.ROOT,
                "INSERT INTO %s (item_name,item_price) VALUES('%s','%.2f');",
                DATA_BASE_NAME, entity.getItemName(), entity.getItemPrice());
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return entity;
    }

    @Override
    public Optional<Item> get(Long id) {
        String query = String.format(Locale.ROOT, "SELECT * FROM %s WHERE item_id='%d';",
                DATA_BASE_NAME, id);
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Long itemId = rs.getLong("item_id");
                String itemName = rs.getString("item_name");
                Double itemPrice = rs.getDouble("item_price");
                Item item = new Item();
                item.setItemId(itemId);
                item.setItemName(itemName);
                item.setItemPrice(itemPrice);
                return Optional.of(item);
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return Optional.empty();
    }

    @Override
    public Item update(Item item) {
        String query = String.format(Locale.ROOT,
                "UPDATE FROM %s SET item_id='%d', item_name='%s', item_price='%.2f';",
                DATA_BASE_NAME, item.getItemName(), item.getItemName(), item.getItemPrice());
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return item;
    }

    @Override
    public boolean deleteId(Long id) {
        String query = String.format("DELETE FROM %s WHERE item_id='%d';",
                DATA_BASE_NAME, id);
        try (Statement statement = connection.createStatement()) {
            statement.executeQuery(query);
            return true;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public boolean delete(Item item) {
        return deleteId(item.getItemId());
    }

    @Override
    public List<Item> getAll() {
        List<Item> items = new ArrayList<>();
        String query = String.format("SELECTE * FROM %s;",
                DATA_BASE_NAME);
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Long itemId = rs.getLong("item_id");
                String itemName = rs.getString("item_name");
                Double itemPrice = rs.getDouble("item_price");
                Item item = new Item();
                item.setItemId(itemId);
                item.setItemName(itemName);
                item.setItemPrice(itemPrice);
                items.add(item);
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return items;
    }
}
