package com.whatwillieat.meals.service;

import com.whatwillieat.meals.configuration.AuthConfig;
import com.whatwillieat.meals.security.AuthenticationMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private static final String AUTH_TOKEN_HEADER_NAME = "X-API-KEY";

    public static Authentication getAuthentication(HttpServletRequest request) {
        String requestApiKey = request.getHeader(AUTH_TOKEN_HEADER_NAME);
        String authToken = request.getHeader(AUTH_TOKEN_HEADER_NAME);

        if (requestApiKey == null || !requestApiKey.equals(authToken)) {
            throw new BadCredentialsException("Invalid API Key");
        }

        return new AuthenticationMapper(requestApiKey, AuthorityUtils.NO_AUTHORITIES);
    }
}
