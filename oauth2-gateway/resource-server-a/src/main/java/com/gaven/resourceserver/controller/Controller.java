package com.gaven.resourceserver.controller;


import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class Controller {

    @Autowired
    private KeycloakRestTemplate restTemplate;
//    private RestTemplate restTemplate;

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

    @GetMapping("/resource/admin")
    @PreAuthorize("hasRole('admin')")
    public String admin() {
        return "<h1>Welcome to the admin endpoint</h1>";
    }

    @GetMapping("/resource/user")
    @PreAuthorize("hasAnyRole('user', 'admin')")
    public String user() {
        return "<h1>Welcome to the user endpoint</h1>";
    }

    @GetMapping("/resource/home")
    public String home() {
        return "<h1>welcome</h1>";
    }

    @GetMapping("/resource/b")
//    @PreAuthorize("hasRole('admin')")
    public String resource_b() {
        return restTemplate.getForObject("http://localhost:9001/", String.class);
    }
}
