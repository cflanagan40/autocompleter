package com.example.autocomplete.service;

import com.example.autocomplete.dao.Completer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SuggestionServiceTest {

    @MockBean
    Completer completer;

    @Autowired
    SuggestionService suggestionService;


    @Test
    void when_query_and_no_limit_then_limit_25() {
        Mockito.when(completer.findMatches(ArgumentMatchers.any(),ArgumentMatchers.eq(25))).thenReturn(testData());

        Assertions.assertThat(suggestionService.suggest("b", Optional.empty()).getSuggestion()).isNotEmpty();

        Mockito.verify(completer).findMatches("b", 25);
    }

    @Test
    void when_query_and_limit_then_limit() {
        Mockito.when(completer.findMatches(ArgumentMatchers.any(),ArgumentMatchers.eq(2))).thenReturn(testData());

        Assertions.assertThat(suggestionService.suggest("b", Optional.of(2)).getSuggestion()).isNotEmpty();

        Mockito.verify(completer).findMatches("b", 2);
    }

    @Test
    void when_limit_over_50_then_limit_50() {
        Mockito.when(completer.findMatches(ArgumentMatchers.any(),ArgumentMatchers.eq(50))).thenReturn(testData());

        Assertions.assertThat(suggestionService.suggest("b", Optional.of(100)).getSuggestion()).isNotEmpty();

        Mockito.verify(completer).findMatches("b", 50);
    }

    @Test
    void when_limit_negative_then_limit_default() {
        Mockito.when(completer.findMatches(ArgumentMatchers.any(),ArgumentMatchers.eq(25))).thenReturn(testData());

        Assertions.assertThat(suggestionService.suggest("b", Optional.of(-1)).getSuggestion()).isNotEmpty();

        Mockito.verify(completer).findMatches("b", 25);
    }

    private Collection<String> testData() {
        Collection<String> suggestions = new ArrayList<>();

        try (Stream<String> lines = Files.lines(Path.of("src/test/resources/test-words.txt"))) {
            suggestions = lines.collect(Collectors.toList());
        } catch (IOException e) {

        }
        return suggestions;
    }
}