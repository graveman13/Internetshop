package mate.academy.internetshop.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import java.util.stream.Collectors;


import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.dao.ItemDao;
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
    public Bucket create(Bucket bucket) {
        return bucketDao.create(bucket);
    }

    @Override
    public Bucket get(Long id) {
        return bucketDao.get(id).get();
    }

    @Override
    public Bucket update(Bucket bucket) {
        return bucketDao.update(bucket);
    }

    @Override
    public boolean deleteId(Long id) {
        return bucketDao.deleteId(id);
    }

    @Override
    public boolean delete(Bucket bucket) {
        return bucketDao.delete(bucket);
    }

    @Override
    public List<Bucket> getAll() {
        return bucketDao.getAll();
    }

    @Override
    public void addItem(Bucket bucket, Item item) {
        bucket.getItems().add(item);
        bucketDao.update(bucket);
    }

    @Override
    public void deleteItem(Bucket bucket, Item item) {
        bucket.getItems().remove(item);
        bucket.deleteItem(item);
        bucketDao.update(bucket);
    }

    @Override
    public void clear(Bucket bucket) {
        bucket.getItems().clear();
        bucketDao.update(bucket);
    }

    @Override
    public Bucket getBucket(Long userId) {
        return bucketDao.getAll().stream()
                .filter(bucket -> bucket.getUserId()
                        .equals(userId)).findAny().get();
    }

    @Override
    public List<Item> getAllItems(Bucket bucket) {
        Bucket bucketTmp = bucketDao.get(bucket.getBucketId()).get();
        return bucketTmp.getItems();
    }

    @Override
    public List<Bucket> getAllBucketByUser(Long userId) {
        return bucketDao.getAll().stream()
                .filter(bucket -> bucket.getUserId().equals(userId))
                .collect(Collectors.toList());
    }
}
