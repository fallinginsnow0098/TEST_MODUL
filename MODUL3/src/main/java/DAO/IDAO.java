package DAO;

import Model.Product;

import java.util.List;

public interface IDAO<T> {
    boolean add(T t, int id);

    boolean update(T t, int categoryId);

    boolean delete(int id);
    List<T> getAll();
    T get(int id);
}
