package mate.academy.internetshop.service;

import java.util.List;

public interface GenericService<T, I> {
    T create(T entity);

    T get(Long id);

    T update(T entity);

    boolean deleteId(I id);

    boolean delete(T entity);

    List<T> getAll();
}
