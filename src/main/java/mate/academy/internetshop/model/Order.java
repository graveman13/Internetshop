package mate.academy.internetshop.model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private Long orderId;
    private List<Item> items;
    private Long userId;

    public Order() {
        items = new ArrayList<>();
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Order order = (Order) o;
        return this.orderId.equals(order.getOrderId())
                && this.items.equals(order.getItems())
                && this.userId.equals(order.getUserId());
    }

    @Override
    public int hashCode() {
        int prime = 31;
        prime = prime + (orderId == null ? 0 : orderId.hashCode());
        prime = prime + (items == null ? 0 : items.hashCode());
        prime = prime + (userId == null ? 0 : userId.hashCode());
        return prime;
    }
}
