package com.cognizant.gateway.filter;

import com.cognizant.gateway.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    @Autowired
    private JwtUtil jwtUtil;

    public JwtAuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            
            // Skip authentication for login and signup endpoints
            String path = request.getURI().getPath();
            if (path.contains("/api/auth/login") || path.contains("/api/auth/signup")) {
                return chain.filter(exchange);
            }

            // Extract Authorization header
            String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return onError(exchange, "Missing or invalid Authorization header", HttpStatus.UNAUTHORIZED);
            }

            String token = authHeader.substring(7);
            
            try {
                // Validate JWT token
                if (!jwtUtil.validateToken(token)) {
                    return onError(exchange, "Invalid or expired JWT token", HttpStatus.UNAUTHORIZED);
                }

                // Extract user information from token
                // In this system, JWT subject is the user/patient id
                String userId = jwtUtil.extractEmail(token); // subject
                String role = jwtUtil.extractRole(token);

                // Role-based authorization
                if (!isAuthorized(path, role)) {
                    return onError(exchange, "Access denied: Insufficient permissions", HttpStatus.FORBIDDEN);
                }

                // Add user information to request headers for downstream services
                ServerHttpRequest modifiedRequest = request.mutate()
                        .header("X-User-Id", userId != null ? userId : "")
                        .header("X-User-Role", role != null ? role : "")
                        .build();

                ServerWebExchange modifiedExchange = exchange.mutate()
                        .request(modifiedRequest)
                        .build();

                return chain.filter(modifiedExchange);
                
            } catch (Exception e) {
                return onError(exchange, "JWT token validation failed", HttpStatus.UNAUTHORIZED);
            }
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        response.getHeaders().add("Content-Type", "application/json");
        
        String body = "{\"error\":\"" + err + "\"}";
        return response.writeWith(Mono.just(response.bufferFactory().wrap(body.getBytes())));
    }

    private boolean isAuthorized(String path, String role) {
        if (role == null) return false;
        
        // Role-based path authorization
        if (path.startsWith("/api/logged/patient/")) {
            return "ROLE_PATIENT".equals(role) || "ROLE_ADMIN".equals(role);
        }
        if (path.startsWith("/api/logged/doctor/")) {
            return "ROLE_DOCTOR".equals(role);
        }
        if (path.startsWith("/api/appointment/")) {
            return "ROLE_DOCTOR".equals(role) || "ROLE_PATIENT".equals(role) || "ROLE_ADMIN".equals(role);
        }
        if (path.startsWith("/api/patient/")) {
            return "ROLE_PATIENT".equals(role) || "ROLE_ADMIN".equals(role);
        }
        if (path.startsWith("/api/doctors/")) {
            return "ROLE_DOCTOR".equals(role) || "ROLE_ADMIN".equals(role);
        }
        if (path.startsWith("/api/admin/")) {
            return "ROLE_ADMIN".equals(role);
        }
        if (path.startsWith("/api/departments/")) {
            return "ROLE_DOCTOR".equals(role) || "ROLE_ADMIN".equals(role);
        }
        if (path.startsWith("/api/medical-records/")) {
            return "ROLE_DOCTOR".equals(role) || "ROLE_PATIENT".equals(role) || "ROLE_ADMIN".equals(role);
        }
        if (path.startsWith("/api/billing/")) {
            return "ROLE_PATIENT".equals(role) || "ROLE_ADMIN".equals(role);
        }
        if (path.startsWith("/api/medicines/")) {
            return "ROLE_DOCTOR".equals(role) || "ROLE_ADMIN".equals(role);
        }
        
        // Default: allow if authenticated (for other endpoints)
        return true;
    }

    public static class Config {
        // Configuration properties can be added here if needed
    }
}
