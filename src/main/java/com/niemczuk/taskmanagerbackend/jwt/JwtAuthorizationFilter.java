package com.niemczuk.taskmanagerbackend.jwt;

import com.niemczuk.taskmanagerbackend.model.dto.authentication.JwtUserDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private static final String HEADER_PREFIX = "Bearer ";

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String jwt = parseJwt(request);

        if (jwt != null && JwtUtility.tokenVerify(jwt)) {
            if (Objects.isNull(SecurityContextHolder.getContext().getAuthentication()) && JwtUtility.tokenVerify(jwt)) {
                JwtUserDetails jwtUserDetails = JwtUtility.getJwtUserDetails(jwt);
                if (jwtUserDetails != null) {
                    configureSecurityContextHolder(request, jwtUserDetails);
                }
            }
        }

        chain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String httpAuthHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(httpAuthHeader) && httpAuthHeader.startsWith(HEADER_PREFIX)) {
            return httpAuthHeader.replace(HEADER_PREFIX, "");
        }

        logger.error("Auth header is invalid");
        return null;
    }

    private void configureSecurityContextHolder(HttpServletRequest request, JwtUserDetails jwtUserDetails) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        jwtUserDetails.getUsername(),
                        null,
                        jwtUserDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
