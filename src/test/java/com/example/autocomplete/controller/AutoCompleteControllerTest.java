package com.example.autocomplete.controller;

import com.example.autocomplete.model.Suggestions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AutoCompleteControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void when_limit_not_set_return() {
        String uri = "http://localhost:"+port+"/autocomplete/v1/suggest/{query}";

        Map<String, String> uriParam = new HashMap<>();
        uriParam.put("query", "b");

        UriComponents builder = UriComponentsBuilder.fromHttpUrl(uri).build();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);

        ResponseEntity<Suggestions> response = testRestTemplate.exchange(builder.toUriString(), HttpMethod.GET, requestEntity,
                Suggestions.class,uriParam);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void when_limit_set_return_only_limit() {
        String uri = "http://localhost:"+port+"/autocomplete/v1/suggest/{query}";

        Map<String, String> uriParam = new HashMap<>();
        uriParam.put("query", "b");

        UriComponents builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("limit", "2")
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);

        ResponseEntity<Suggestions> response = testRestTemplate.exchange(builder.toUriString(), HttpMethod.GET, requestEntity,
                Suggestions.class,uriParam);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().getSuggestion().size());
    }

    @Test
    void when_not_found_return_404() {
        String uri = "http://localhost:"+port+"/autocomplete/v1/suggest/{query}";

        Map<String, String> uriParam = new HashMap<>();
        uriParam.put("query", "zzzz");

        UriComponents builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("limit", "2")
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);

        ResponseEntity<Suggestions> response = testRestTemplate.exchange(builder.toUriString(), HttpMethod.GET, requestEntity,
                Suggestions.class,uriParam);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}