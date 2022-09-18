package com.example.autocomplete.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Completer implementation using H2 backend for search
 */
@Repository
@RequiredArgsConstructor
public class H2CompleterDao implements Completer<Collection<String>, String> {

    private final static String SELECT_QUERY = "select word from words where word LIKE ? LIMIT ?";

    private final JdbcTemplate jdbcTemplate;

    /**
     * Look up a string or fragment and return fuzzy matches up to limit
     * @param query
     * @param limit
     * @return
     */
    @Override
    public Collection<String> findMatches(String query, Integer limit) {
        //Add validation - TODO

        return jdbcTemplate.queryForList(SELECT_QUERY, String.class, String.format("%s%s", query, "%"), limit);
    }
}
