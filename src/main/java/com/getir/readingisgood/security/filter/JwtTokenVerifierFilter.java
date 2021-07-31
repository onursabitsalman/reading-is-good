package com.getir.readingisgood.security.filter;

import com.getir.readingisgood.security.JwtConfig;
import com.getir.readingisgood.service.UserTokenService;
import com.google.common.base.Strings;
import com.google.common.net.HttpHeaders;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class JwtTokenVerifierFilter extends OncePerRequestFilter {

    private final JwtConfig jwtConfig;
    private final UserTokenService userTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (Strings.isNullOrEmpty(authorizationHeader)
                || !authorizationHeader.startsWith(JwtConfig.AUTHORIZATION_PREFIX)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        String token = authorizationHeader.replace(JwtConfig.AUTHORIZATION_PREFIX, "");
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(jwtConfig.secretKey()).build()
                    .parseClaimsJws(token);
            Claims body = claimsJws.getBody();
            String username = body.getSubject();
            if (!userTokenService.existUserToken(username)) {
                filterChain.doFilter(httpServletRequest, httpServletResponse);
                return;
            }
            var authorities = (List<Map<String, String>>) body.get(JwtConfig.CLAIM_KEY);
            Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
                    .map(m -> new SimpleGrantedAuthority(m.get("authority"))).collect(Collectors.toSet());
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null,
                    simpleGrantedAuthorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (JwtException e) {
            throw new IllegalStateException(String.format("Token %s cannot be trusted", token));
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
