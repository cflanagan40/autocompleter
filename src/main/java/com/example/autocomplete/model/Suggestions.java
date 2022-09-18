package com.example.autocomplete.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Suggestions {
    private List<String> suggestion = new ArrayList<>();
}
