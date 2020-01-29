package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;

@Dao
public class BucketDaoImpl implements BucketDao {
    private static Long bucketId = 0L;

    @Override
    public Bucket create(Bucket bucket) {
        bucket.setBucketId(bucketId++);
        Storage.buckets.add(bucket);
        return bucket;
    }

    @Override
    public Optional<Bucket> get(Long id) {
        return Optional.of(Storage.buckets.stream()
                .filter(bucket -> bucket.getBucketId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Can't find bucket with id " + id)));
    }

    @Override
    public Bucket update(Bucket bucket) {
        Bucket bucketTmp = Storage.buckets
                .stream()
                .filter(b -> b.getBucketId().equals(bucket.getBucketId()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Can't find bucket"));
        delete(bucketTmp);
        Storage.buckets.add(bucket);
        return bucket;
    }

    @Override
    public boolean deleteId(Long id) {
        return Storage.buckets.removeIf(bucket -> bucket.getBucketId().equals(id));
    }

    @Override
    public boolean delete(Bucket bucket) {
        return deleteId(bucket.getBucketId());
    }

    @Override
    public List<Bucket> getAll() {
        return Storage.buckets;
    }

    @Override
    public List<Item> getItemsByBucket(Long id) {
        return null;
    }

    @Override
    public Optional<Bucket> getByUserId(Long id) {
        return Optional.empty();
    }
}
