package com.example.autocomplete.service;

import com.example.autocomplete.dao.Completer;
import com.example.autocomplete.model.Suggestions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SuggestionService {

    private final Completer<Collection<String>, String> completer;

    /**
     * Given a string fragment, suggest words that fragment could create
     *
     * @param query
     * @param maybeLimit
     * @return
     */
    public Suggestions suggest(String query, Optional<Integer> maybeLimit) {
        //Check cache - TODO
        //Track usage - TODO


        Integer limit = maybeLimit.orElse(25);

        //We only want a max of 50 instead of error we'll set to 50 if greater
        if (limit > 50) limit = 50;

        if (limit < 1) limit = 25;

        Suggestions suggestions = new Suggestions();

        //No cache hit return DB call
        completer.findMatches(query, limit)
                .stream()
                .forEach(suggestions.getSuggestion()::add);

        return suggestions;
    }
}
