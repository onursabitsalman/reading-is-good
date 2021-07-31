package com.getir.readingisgood.service;

import com.getir.readingisgood.model.response.GenericReturnValue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserTokenService userTokenService;

    public GenericReturnValue<Boolean> logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userTokenService.deleteToken(authentication.getName());
        return new GenericReturnValue<>(true);
    }
}
