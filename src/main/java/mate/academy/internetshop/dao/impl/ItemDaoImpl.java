package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Item;

@Dao
public class ItemDaoImpl implements ItemDao {
    private static Long itemId = 0L;

    @Override
    public Item create(Item item) {
        item.setItemId(itemId++);
        Storage.items.add(item);
        return item;
    }

    @Override
    public Optional<Item> get(Long id) {
        return Optional.ofNullable(Storage.items.stream()
                .filter(item -> item.getItemId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Can't find item with id " + id)));
    }

    @Override
    public Item update(Item item) {
        deleteId(item.getItemId());
        Storage.items.add(item);
        return item;
    }

    @Override
    public boolean deleteId(Long id) {
        return Storage.items.removeIf(item -> item.getItemId().equals(id));
    }

    @Override
    public boolean delete(Item item) {
        return deleteId(item.getItemId());
    }

    @Override
    public List<Item> getAll() {
        return Storage.items;
    }
}
