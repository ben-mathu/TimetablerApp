package com.example.timetablerapp.data;

/**
 * 06/05/19 -bernard
 */
public interface DataSource<T, T2, T3> {
    void update(T2 item);
    void delete(T2 item);
    void save(T2 item);
}
