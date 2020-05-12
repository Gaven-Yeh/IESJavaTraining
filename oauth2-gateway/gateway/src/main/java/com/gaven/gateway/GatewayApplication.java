package com.gaven.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.cloud.security.oauth2.gateway.TokenRelayGatewayFilterFactory;

@SpringBootApplication
public class GatewayApplication {

    @Autowired
    private TokenRelayGatewayFilterFactory filterFactory;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
//                .route("resource", r -> r.path("/resource/user")
//                    .filters(f -> f.filters(filterFactory.apply())
//                            .removeRequestHeader("Cookie")) // Prevents cookie being sent downstream
//                    .uri("http://localhost:9000/user")) // TODO: try using eureka service discovery instead
//                .route("resource", r -> r.path("/resource/admin")
//                    .filters(f -> f.filters(filterFactory.apply())
//                            .removeRequestHeader("Cookie")) // Prevents cookie being sent downstream
//                    .uri("http://localhost:9000/admin"))
//                .route("resource", r -> r.path("/resource/home")
//                    .filters(f -> f.filters(filterFactory.apply())
//                            .removeRequestHeader("Cookie")) // Prevents cookie being sent downstream
//                    .uri("http://localhost:9000/home"))
                .route("resource", r -> r.path("/resource/**")
                    .filters(f -> f.filters(filterFactory.apply())
                            .removeRequestHeader("Cookie")) // Prevents cookie being sent downstream
                    .uri("http://localhost:9000"))
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}
