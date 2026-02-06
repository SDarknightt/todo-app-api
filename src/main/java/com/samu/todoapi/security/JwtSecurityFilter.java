package com.samu.todoapi.security;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.samu.todoapi.service.JwtService;
import io.jsonwebtoken.Claims;
import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtSecurityFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    public JwtSecurityFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public boolean shouldNotFilter(HttpServletRequest request) {
        return request.getRequestURI().equals("/login") || (request.getRequestURI().equals("/users") && request.getMethod().equals(HttpMethod.POST.name()));
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
        throws ServletException, IOException {
        System.out.println(request.getHeader("Authorization"));
        Optional<Claims> claimsOptional = getParsedToken(request);

        if (claimsOptional.isEmpty()) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }
        Claims claims = claimsOptional.get();
        final var authentication = new UsernamePasswordAuthenticationToken(
                claims.get("username", String.class),
                null,
                ((List<String>) claims.get("authorities")).stream().map(SimpleGrantedAuthority::new).toList()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Go next filter if ok
        filterChain.doFilter(request, response);
    }

    public Optional<Claims> getParsedToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                        .map(header -> header.replace("Bearer", ""))
                        .map(String::trim)
                        .map(jwtService::extractAllClaims);
    }

}
