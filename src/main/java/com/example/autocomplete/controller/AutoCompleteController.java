package com.example.autocomplete.controller;

import com.example.autocomplete.model.Suggestions;
import com.example.autocomplete.service.SuggestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/autocomplete/v1")
@RequiredArgsConstructor
public class AutoCompleteController {

    private final SuggestionService suggestionService;

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "No match found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal processing error", content = @Content)
    },
            description = "Given a string or part of a string return suggestions for creating a new word"
    )
    @GetMapping(value = "/suggest/{query}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Suggestions> suggest(@PathVariable String query, @RequestParam(name = "limit") Optional<Integer> maybeLimit) {
        try {
            Suggestions suggestions = suggestionService.suggest(query, maybeLimit);

            if (suggestions.getSuggestion().isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(suggestions);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
