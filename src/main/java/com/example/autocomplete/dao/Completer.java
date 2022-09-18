package com.example.autocomplete.dao;

public interface Completer<T,V> {
    T findMatches(V query, Integer limit);
}
