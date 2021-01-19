
package com.wanyos.dao;

import java.util.List;

/**
 * @author wanyos
 * @param <T>
 * @param <K>
 */
public interface DAO<T, K> {
    
    void insert(T s);
    void delete(T s);
    void set(T s);
    List<T> list();
    T get(K s);
}
