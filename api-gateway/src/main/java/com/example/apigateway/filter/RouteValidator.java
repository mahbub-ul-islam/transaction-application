package com.example.apigateway.filter;


import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    private static final List<String> openApiEndpoints = List.of(
            "/auth/register",
            "/auth/login",
            "/eureka",
            "/v3/api-docs/**",            // OpenAPI documentation
            "/swagger-ui/**",             // Swagger UI resources
            "/swagger-ui/index.html"    // Swagger UI HTML page
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints.stream().noneMatch(
                    uri -> request.getURI().getPath().contains(uri));
}
