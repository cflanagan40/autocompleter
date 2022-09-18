package com.example.autocomplete.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class H2CompleterDaoTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    void findMatches() {
        H2CompleterDao dao = new H2CompleterDao(jdbcTemplate);
        Assertions.assertThat(dao.findMatches("b", 25)).containsExactly(
                "banana",
                "berry",
                "bright",
                "buggy",
                "bunch"
        );
    }

    @Test
    void when_limit_then_smaller_set() {
        H2CompleterDao dao = new H2CompleterDao(jdbcTemplate);
        Assertions.assertThat(dao.findMatches("b", 2)).containsExactly(
                "banana",
                "berry"

        );
    }

    @Test
    void when_limit_negative_then_exception() {
        H2CompleterDao dao = new H2CompleterDao(jdbcTemplate);
        org.junit.jupiter.api.Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            dao.findMatches("b", -2);
        });
    }

    @Test
    void when_limit_zero_then_empty_response() {
        H2CompleterDao dao = new H2CompleterDao(jdbcTemplate);
        Assertions.assertThat(dao.findMatches("b", 0)).isEmpty();
    }

    @Test
    void when_no_results_then_empty_list() {
        H2CompleterDao dao = new H2CompleterDao(jdbcTemplate);
        Assertions.assertThat(dao.findMatches("zzzzz", 25)).isEmpty();
    }

    @Test
    void when_nothing_passed_in_then_return_all() {
        H2CompleterDao dao = new H2CompleterDao(jdbcTemplate);
        Assertions.assertThat(dao.findMatches("", 25)).containsExactly(
                "allen",
                "anvil",
                "apple",
                "axe",
                "banana",
                "berry",
                "bright",
                "buggy",
                "bunch"
        );
    }
}