/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoessys.dao;

import java.util.List;

/**
 *
 * @author trann
 */
public abstract class SmartBeesDAO<E, K> {
    abstract public void insert(E entity);
    abstract public void update(E entity);
    abstract public void delete(K key);
    abstract public E selectById(K key);
    abstract public List<E> selectALL();
    abstract protected List<E> selectBySql(String sql, Object...args);
}
