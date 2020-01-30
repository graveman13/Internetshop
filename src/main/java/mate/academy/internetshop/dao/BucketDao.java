package mate.academy.internetshop.dao;

import java.util.List;
import java.util.Optional;

import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;

public interface BucketDao extends GenericDao<Bucket, Long> {
    List<Item> getItemsByBucket(Long id) throws DataProcessingException;

    Optional<Bucket> getByUserId(Long id) throws DataProcessingException;
}
