package com.david.practica.practicaspring.config;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenProvider jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(SecurityConstants.HEADER_STRING);

        if (header == null || header.isEmpty()) {
            logger.debug("couldn't find bearer string, will ignore the header");
            chain.doFilter(req, res);
            return;
        }

        String authToken = header.replace(SecurityConstants.TOKEN_PREFIX, "");
        try {
                String username = jwtTokenUtil.getUsernameFromToken(authToken);
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    UserDetails userDetails = new org.springframework.security.core.userdetails.User(username, "", jwtTokenUtil.getAuthorities(authToken));

                    if (Boolean.TRUE.equals(jwtTokenUtil.validateToken(authToken, userDetails))) {
                        UsernamePasswordAuthenticationToken authentication = jwtTokenUtil.getAuthentication(authToken, userDetails);
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                        logger.debug("authenticated user " + username + ", setting security context");
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
        } catch (IllegalArgumentException e) {
            logger.error("an error occured during getting username from token: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.warn("the token is expired and not valid anymore");
        } catch (SignatureException e) {
            logger.error("Authentication Failed. Username or Password not valid.");
        } catch (MalformedJwtException e) {
            logger.error("Malformed JWT Token Exception: " + e.getMessage());
        } finally {
            chain.doFilter(req, res);
        }
    }
}

