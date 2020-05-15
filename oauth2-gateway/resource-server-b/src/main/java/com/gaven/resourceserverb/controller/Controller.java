package com.gaven.resourceserverb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @GetMapping
    public String resource_b() {
        return "<h1>This is resource b</h1>";
    }
}
