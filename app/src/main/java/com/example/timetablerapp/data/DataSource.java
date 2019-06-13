package com.example.timetablerapp.data;

/**
 * 06/05/19 -bernard
 */
public interface DataSource<T> {
    void update(T item);
    void delete(T item);
    void save(T item);
}
