package com.gaven.resourceserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;

//@EnableWebSecurity
//public class ResourceServerConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests(authorizeRequests ->
//                        authorizeRequests
//                                .mvcMatchers("/admin/**").access("hasAuthority('SCOPE_admin')")
//                                .mvcMatchers("/user/**").access("hasAnyAuthority('SCOPE_user', 'SCOPE_admin')")
//                                .mvcMatchers("/home/**").access("permitAll()")
////                                .mvcMatchers("/admin/**").access("permitAll()")
////                                .mvcMatchers("/user/**").access("permitAll()")
//                                .anyRequest().authenticated())
//                .oauth2ResourceServer()
//                .jwt();
//    }
//}

//@EnableWebFluxSecurity
public class ResourceServerConfig {
//    @Bean
//    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) throws Exception {
//        http
//                .authorizeExchange()
//                    .pathMatchers("/resource/**")
//                        .hasAuthority("SCOPE_resource.read")
////                    .pathMatchers("/resource/admin/**")
////                        .hasRole("ROLE_admin")
////                   .pathMatchers("/resource/user/**")
////                        .hasAnyRole("ROLE_user", "ROLE_admin")
//
//                .anyExchange()
//                        .authenticated()
//                    .and()
//                .oauth2ResourceServer()
//                    .jwt();
//        return http.build();
//    }
}

