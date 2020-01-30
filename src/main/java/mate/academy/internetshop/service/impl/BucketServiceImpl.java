package mate.academy.internetshop.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Service;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.service.BucketService;

@Service
public class BucketServiceImpl implements BucketService {
    @Inject
    private static BucketDao bucketDao;
    @Inject
    private static ItemDao itemDao;

    @Override
    public Bucket create(Bucket bucket) throws DataProcessingException {
        return bucketDao.create(bucket);
    }

    @Override
    public Bucket get(Long id) throws DataProcessingException {
        return bucketDao.get(id).get();
    }

    @Override
    public Bucket update(Bucket bucket) throws DataProcessingException {
        return bucketDao.update(bucket);
    }

    @Override
    public boolean deleteId(Long id) throws DataProcessingException {
        return bucketDao.deleteId(id);
    }

    @Override
    public boolean delete(Bucket bucket) throws DataProcessingException {
        return bucketDao.delete(bucket);
    }

    @Override
    public List<Bucket> getAll() throws DataProcessingException {
        return bucketDao.getAll();
    }

    @Override
    public void addItem(Bucket bucket, Item item) throws DataProcessingException {
        bucket.getItems().add(item);
        bucketDao.update(bucket);
    }

    @Override
    public void deleteItem(Bucket bucket, Item item) throws DataProcessingException {
        bucket.getItems().remove(item);
        bucket.deleteItem(item);
        bucketDao.update(bucket);
    }

    @Override
    public void clear(Bucket bucket) throws DataProcessingException {
        bucket.getItems().clear();
        bucketDao.update(bucket);
    }

    @Override
    public Bucket getBucket(Long userId) throws DataProcessingException {
        return bucketDao.getByUserId(userId).get();
    }

    @Override
    public List<Item> getAllItems(Bucket bucket) throws DataProcessingException {
        Bucket bucketTmp = bucketDao.get(bucket.getBucketId()).get();
        return bucketTmp.getItems();
    }

    @Override
    public List<Bucket> getAllBucketByUser(Long userId) throws DataProcessingException {
        return bucketDao.getAll().stream()
                .filter(bucket -> bucket.getUserId().equals(userId))
                .collect(Collectors.toList());
    }
}
