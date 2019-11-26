package com.example.timetablerapp.data;

import com.example.timetablerapp.util.SuccessfulCallback;

/**
 * 06/05/19 -bernard
 */
public interface DataSource<T> {
    void update(T item, SuccessfulCallback callback);
    void delete(T item, SuccessfulCallback callback);
    void save(T item, SuccessfulCallback callback);
}
