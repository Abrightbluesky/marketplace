package com.ruth.shop.security;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtFilter extends GenericFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil){
        this.jwtUtil = jwtUtil;
    }
    @Override
public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse res = (HttpServletResponse) response;

    String path = req.getRequestURI();
    System.out.println("PATH: " + path);

    // 🔥 IZINKAN LOGIN & REGISTER
    if(path.contains("/auth") || path.contains("/error")){
    chain.doFilter(request, response);
    return;
}

    String header = req.getHeader("Authorization");
    System.out.println("HEADER: " + header);

    if(header == null || !header.startsWith("Bearer ")){
        res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        return;
    }

    String token = header.substring(7);

    try {
        jwtUtil.extractEmail(token);
    } catch (Exception e){
        res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        return;
    }

    chain.doFilter(request, response);
}

    
}