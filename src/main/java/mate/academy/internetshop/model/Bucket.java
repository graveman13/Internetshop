package mate.academy.internetshop.model;

import java.util.ArrayList;
import java.util.List;

public class Bucket {
    private Long bucketId;
    private List<Item> items;
    private Long userId;

    public Bucket() {
        items = new ArrayList<>();
    }

    public Long getBucketId() {
        return bucketId;
    }

    public void setBucketId(Long bucketId) {
        this.bucketId = bucketId;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void setItem(Item item) {
        this.items.add(item);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUser(Long userId) {
        this.userId = userId;
    }

    public void deleteItem(Item item) {
        items.remove(item);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Bucket bucket = (Bucket) o;
        return bucketId.equals(bucket.getBucketId())
                && items.equals(bucket.getItems())
                && userId.equals(bucket.getUserId());
    }

    @Override
    public int hashCode() {
        int prime = 31;
        prime = prime + (bucketId == null ? 0 : bucketId.hashCode());
        prime = prime + (items == null ? 0 : items.hashCode());
        prime = prime + (userId == null ? 0 : userId.hashCode());
        return prime;
    }

    @Override
    public String toString() {
        return "Bucket "
                + "bucketId = " + bucketId + ", items=" + items
                + ", userId=" + userId;
    }
}
