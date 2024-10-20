package com.example.apigateway.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator routeValidator;
    @Autowired
    private RestTemplate restTemplate;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (routeValidator.isSecured.test(exchange.getRequest())) {

                if (!exchange.getRequest().getHeaders().containsKey(AUTHORIZATION)) {
                    throw new RuntimeException("Missing Authorization header");
                }

                String authHeader = exchange.getRequest().getHeaders().get(AUTHORIZATION).get(0);
                String token = "";
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    token = authHeader.substring(7);
                }
                log.info("Sending token for validation: " + token);

                try {
                    String response =
                            restTemplate.getForObject("http://localhost:8085/api/v1/auth/validate?token=" + token,
                                    String.class);
                    log.info("Response from token validation: " + response);

                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode jsonResponse = mapper.readTree(response);
                    boolean isSuccess = jsonResponse.get("success").asBoolean();

                    if (!isSuccess) {
                        log.error("Validation failed response: " + jsonResponse);
                        throw new RuntimeException("Unauthorized token.");
                    }
                } catch (Exception e) {
                    log.error("Unauthorized token: " + e.getMessage());
                    throw new RuntimeException("Unauthorized token.");
                }
            }
            log.info("Token validation successful.");
            return chain.filter(exchange);
        });
    }

    public static class Config {
    }

    @Override
    public String name() {
        return "AuthenticationFilter";
    }
}
