package com.gaven.resourceserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class Controller {

    @Autowired
    private RestTemplate restTemplate;

//    @GetMapping("/resource/user")
//    public String user(@AuthenticationPrincipal Jwt jwt) {
//        return String.format("User resource accessed by: %s (with subjectId: %s)",
//                jwt.getClaims().get("user_name"),
//                jwt.getSubject());
//    }
//
//    @GetMapping("/resource/admin")
//    public String admin(@AuthenticationPrincipal Jwt jwt) {
//        return String.format("Admin resource accessed by: %s (with subjectId: %s) token: %s",
//                jwt.getClaims().get("user_name"),
//                jwt.getSubject(),
//                jwt.getTokenValue());
//    }

    @GetMapping("/resource/user")
    public String user() {
        return "<h1>Welcome to the user endpoint</h1>";
    }

    @GetMapping("/resource/admin")
    public String admin() {
        return "<h1>Welcome to the admin endpoint</h1>";
    }

    @GetMapping("/resource/home")
    public String home() {
        return "<h1>welcome</h1>";
    }

    @GetMapping("resource/b")
    public String resource_b() {
        return restTemplate.getForObject("http://resource-b/", String.class);
    }
}
