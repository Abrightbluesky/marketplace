package com.ruth.shop.security;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.stereotype.Component;



import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.Collections;

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
       String email =   jwtUtil.extractEmail(token);
       String role = jwtUtil.extractRole(token);

       UsernamePasswordAuthenticationToken auth =
       new UsernamePasswordAuthenticationToken(
        email,
        null,
        Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role)));

        SecurityContextHolder.getContext().setAuthentication(auth);

        // ambil role dari token 
        // baris ini salah//
         /* String role = Jwts.parserBuilder()
                        .setSigningKey(jwtUtil.getKey())
                        .build()
                        .parseClaimsJws(token)
                        .getBody()
                        .get("role", String.class);

        // kasih ke Spring security 
        UsernamePasswordAuthenticationToken auth = 
        new UsernamePasswordAuthenticationToken(
            email,
            null,
            Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + role)
            )
        );
        
        SecurityContextHolder.getContext().setAuthentication(auth); */ 

        System.out.println("AUTH SET: " + email);
        System.out.println("TOKEN:" + token);
        System.out.println("EMAIL FROM TOKEN:" + token);
        System.out.println("ROLE:" + role);
    } catch (Exception e){
        res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        return;
    }

    chain.doFilter(request, response);
}

    
}