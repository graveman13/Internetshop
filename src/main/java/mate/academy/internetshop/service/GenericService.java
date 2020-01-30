package mate.academy.internetshop.service;

import java.util.List;

import mate.academy.internetshop.exceptions.DataProcessingException;

public interface GenericService<T, I> {
    T create(T entity) throws DataProcessingException;

    T get(Long id) throws DataProcessingException;

    T update(T entity) throws DataProcessingException;

    boolean deleteId(I id) throws DataProcessingException;

    boolean delete(T entity) throws DataProcessingException;

    List<T> getAll() throws DataProcessingException;
}
