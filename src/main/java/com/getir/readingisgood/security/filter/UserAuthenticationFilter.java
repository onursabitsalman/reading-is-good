package com.getir.readingisgood.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.getir.readingisgood.entity.UserToken;
import com.getir.readingisgood.exceptions.UnauthorizedException;
import com.getir.readingisgood.model.request.AuthenticationRequest;
import com.getir.readingisgood.security.JwtConfig;
import com.getir.readingisgood.security.UserDetailsDao;
import com.getir.readingisgood.service.UserTokenService;
import com.getir.readingisgood.utils.ErrorMessages;
import com.google.common.net.HttpHeaders;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;

public class UserAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtConfig jwtConfig;
    private final UserTokenService userTokenService;

    public UserAuthenticationFilter(AuthenticationManager authenticationManager, JwtConfig jwtConfig, UserTokenService userTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtConfig = jwtConfig;
        this.userTokenService = userTokenService;

        setFilterProcessesUrl("/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            AuthenticationRequest authenticationRequest = new ObjectMapper().readValue(request.getInputStream(),
                    AuthenticationRequest.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                    authenticationRequest.getPassword(), new HashSet<>());
            return authenticationManager.authenticate(authentication);
        } catch (IOException e) {
            throw new UnauthorizedException(ErrorMessages.AUTHENTICATION_ERROR);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        UserDetailsDao userDetailsDao = (UserDetailsDao) authResult.getPrincipal();
        String token = Jwts.builder().setSubject(authResult.getName())
                .claim(JwtConfig.CLAIM_KEY, authResult.getAuthorities())
                .claim(JwtConfig.CLAIMS_ID, userDetailsDao.getId()).setIssuedAt(new Date())
                .setExpiration(jwtConfig.jwtExpiredTime()).signWith(jwtConfig.secretKey()).compact();
        response.addHeader(HttpHeaders.AUTHORIZATION, JwtConfig.AUTHORIZATION_PREFIX + token);
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        userTokenService.setToken(new UserToken(token, authResult.getName(), ipAddress));
    }
}
