package org.example.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    // ✅ Skip this filter for public endpoints
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        String method = request.getMethod();

        return path.startsWith("/api/auth") ||
                (path.startsWith("/api/products") && method.equals("GET")) ||
                (path.startsWith("/api/categories") && method.equals("GET")) || // ⬅️ Optional: Allow public category viewing
                path.startsWith("/uploads") ||
                path.startsWith("/images");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // ✅ Validate Bearer token
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            if (jwtUtil.validateToken(token)) {
                String email = jwtUtil.getEmailFromToken(token);
                String role = jwtUtil.getRoleFromToken(token); // 🆕 Extract role from token

                // ✅ Convert role to Spring authority format: "ROLE_ADMIN", "ROLE_USER", etc.
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);

                // ✅ Attach user info and authority
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(email, null, List.of(authority));

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // ✅ Set the security context with the authenticated user
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // ✅ Continue processing
        filterChain.doFilter(request, response);
    }
}
