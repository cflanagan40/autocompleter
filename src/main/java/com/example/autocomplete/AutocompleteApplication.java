package com.example.autocomplete;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AutocompleteApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(AutocompleteApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //load words to db at start up

    }
}
