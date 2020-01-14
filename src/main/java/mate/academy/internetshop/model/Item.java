package mate.academy.internetshop.model;

public class Item {
    private Long itemId;
    private String itemName;
    private Double itemPrice;
    private Integer counter;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return this.itemId.equals(item.getItemId())
                && this.itemName.equals(item.getItemName())
                && this.itemPrice.equals(item.getItemPrice());
    }

    @Override
    public int hashCode() {
        int prime = 31;
        prime = prime + (itemId == null ? 0 : itemId.hashCode());
        prime = prime + (itemPrice == null ? 0 : itemPrice.hashCode());
        prime = prime + (itemName == null ? 0 : itemName.hashCode());
        return prime;
    }

    @Override
    public String toString() {
        return "Item "
                + "itemId=" + itemId
                + ", itemName='" + itemName
                + ", itemPrice=" + itemPrice
                + ", counter=" + counter;
    }
}
