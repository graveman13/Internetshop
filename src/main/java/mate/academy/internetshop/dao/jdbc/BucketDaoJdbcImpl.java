package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import org.apache.log4j.Logger;

@Dao
public class BucketDaoJdbcImpl extends AbstractDao<Bucket> implements BucketDao {
    private static final Logger LOGGER = Logger.getLogger(BucketDaoJdbcImpl.class);
    private static final String TABLE_BUCKET = "internetshop.bucket";
    private static final String TABLE_BUCKET_ITEMS = "internetshop.bucket_items";

    public BucketDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Bucket create(Bucket bucket) throws DataProcessingException {
        String createBucket = String.format(
                "insert into %s (user_bucket_id) values(?)", TABLE_BUCKET);
        try (PreparedStatement statement =
                     connection.prepareStatement(createBucket, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, bucket.getUserId());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            while (rs.next()) {
                bucket.setBucketId(rs.getLong(1));
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new DataProcessingException("Can't create bucket " + e);
        }
        return bucket;
    }

    @Override
    public Optional<Bucket> get(Long id) throws DataProcessingException {
        String query = String.format("select * from %s where bucket_id=?", TABLE_BUCKET);
        return Optional.of(generalGetByQuery(id, query));
    }

    @Override
    public Optional<Bucket> getByUserId(Long id) throws DataProcessingException {
        String query = String.format("select * from %s where user_bucket_id=?", TABLE_BUCKET);
        return Optional.of(generalGetByQuery(id, query));
    }

    private Bucket generalGetByQuery(Long id, String query) throws DataProcessingException {
        Bucket bucket = new Bucket();
        bucket.setBucketId(id);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                bucket.setBucketId(rs.getLong("bucket_id"));
                bucket.setUser(rs.getLong("user_bucket_id"));
            }
            bucket.setItems(getItemsByBucket(id));
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new DataProcessingException("Can't get bucket " + e);
        }
        return bucket;
    }

    @Override
    public Bucket update(Bucket bucket) throws DataProcessingException {
        List<Item> oldItems = getItemsByBucket(bucket.getBucketId());
        List<Item> newItems = bucket.getItems();
        List<Item> toDelete = new ArrayList<>(oldItems);
        toDelete.removeAll(newItems);
        deleteItems(bucket, toDelete);
        List<Item> toAdd = new ArrayList<>(newItems);
        toAdd.removeAll(oldItems);
        insertItem(bucket, toAdd);
        return bucket;
    }

    public void deleteItems(Bucket bucket, List<Item> items) throws DataProcessingException {
        String query = String.format(
                "delete from %s where bucket_id = ? and item_id =?;", TABLE_BUCKET_ITEMS);
        refreshItemsInBucket(bucket, items, query);
    }

    public void insertItem(Bucket bucket, List<Item> items) throws DataProcessingException {
        String query = String.format("insert into %s  (bucket_id,item_id) values(?,?);",
                TABLE_BUCKET_ITEMS);
        refreshItemsInBucket(bucket, items, query);
    }

    private void refreshItemsInBucket(Bucket bucket, List<Item> items, String query)
            throws DataProcessingException {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (Item item : items) {
                statement.setLong(1, bucket.getBucketId());
                statement.setLong(2, item.getItemId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new DataProcessingException("Can't refrash bucket " + e);
        }
    }

    @Override
    public boolean deleteId(Long id) throws DataProcessingException {
        String deleteBucket = String.format("delete from %s where bucket_id =?;", TABLE_BUCKET);
        try (PreparedStatement statement = connection.prepareStatement(deleteBucket)) {
            statement.setLong(1, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new DataProcessingException("Can't delete bucket " + e);
        }
    }

    @Override
    public boolean delete(Bucket bucket) throws DataProcessingException {
        return deleteId(bucket.getBucketId());
    }

    @Override
    public List<Bucket> getAll() throws DataProcessingException {
        List<Bucket> buckets = new ArrayList<>();
        String getAllBucket = String.format("select * from %s", TABLE_BUCKET);
        try (PreparedStatement statement = connection.prepareStatement(getAllBucket)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                buckets.add(get(rs.getLong("bucket_id")).get());
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new DataProcessingException("Can't get all buckets " + e);
        }
        return buckets;
    }

    @Override
    public List<Item> getItemsByBucket(Long id) throws DataProcessingException {
        List<Item> items = new ArrayList<>();
        String getItems = String.format("select * from %s"
                + " join items on items.item_id = bucket_items.item_id"
                + " where bucket_items.bucket_id = ?;", TABLE_BUCKET_ITEMS);
        try (PreparedStatement statement = connection.prepareStatement(getItems)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                item.setItemId(rs.getLong("item_id"));
                item.setItemName(rs.getString("item_name"));
                item.setItemPrice(rs.getDouble("item_price"));
                items.add(item);
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new DataProcessingException("Can't get items in bucket " + e);
        }
        return items;
    }
}
