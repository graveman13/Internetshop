package mate.academy.internetshop.service;

import java.util.List;

import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;

public interface BucketService extends GenericService<Bucket, Long> {

    void addItem(Bucket bucket, Item item);

    void deleteItem(Bucket bucket, Item item);

    void clear(Bucket bucket);

    Bucket getBucket(Long userId);

    List<Item> getAllItems(Bucket bucket);

    List<Bucket> getAllBucketByUser(Long userId);
  
    List<Item> getAllItems(Bucket bucket);
}
