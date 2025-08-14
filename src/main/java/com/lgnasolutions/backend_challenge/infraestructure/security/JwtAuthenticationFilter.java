package com.lgnasolutions.backend_challenge.infraestructure.security;

import com.lgnasolutions.backend_challenge.application.services.AuthService;
import com.lgnasolutions.backend_challenge.domain.entities.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtProvider jwtProvider;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/api/auth/login") || path.startsWith("/api/auth/register");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        if (!isValidAuthorizationHeader(authorization)) {
            sendErrorResponse(response, "Unauthorized or invalid Bearer Token");
            return;
        }
        String token = extractToken(authorization);

        if (!jwtProvider.validateToken(token)) {
            sendErrorResponse(response, "Unauthorized or invalid Bearer Token");
            return;
        }

        UUID userId = jwtProvider.getUserIdFromToken(token);
        UUID bodyUserId = extractBodyUserId(request);

        if (isUserIdMismatch(request, userId, bodyUserId)) {
            sendErrorResponse(response, "User ID mismatch");
            return;
        }

        request.setAttribute("userId", userId);
        filterChain.doFilter(request, response);
    }

    private boolean isValidAuthorizationHeader(String authorization) {
        return authorization != null && authorization.startsWith("Bearer ");
    }

    private String extractToken(String authorization) {
        return authorization.substring(7);
    }

    private UUID extractBodyUserId(HttpServletRequest request) {
        if ("POST".equalsIgnoreCase(request.getMethod()) || "PUT".equalsIgnoreCase(request.getMethod())) {
            String userIdParam = request.getParameter("userId");
            if (userIdParam != null) {
                try {
                    return UUID.fromString(userIdParam);
                } catch (IllegalArgumentException e) {
                    // logs here if needed
                }
            }
        }
        return null;
    }

    private boolean isUserIdMismatch(HttpServletRequest request, UUID userId, UUID bodyUserId) {
        return bodyUserId != null && !userId.equals(bodyUserId);
    }

    private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("{\"error\": \"" + message + "\"}");
    }
}
