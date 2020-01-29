package mate.academy.internetshop.service;

import java.util.List;

import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.model.Item;

public interface ItemService extends GenericService<Item, Long> {
    List<Item> getAllItem() throws DataProcessingException;
}
