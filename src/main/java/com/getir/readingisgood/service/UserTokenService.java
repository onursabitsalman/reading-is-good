package com.getir.readingisgood.service;

import com.getir.readingisgood.entity.UserToken;
import com.getir.readingisgood.repository.UserTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserTokenService {

    private final UserTokenRepository tokenRepository;

    public void setToken(UserToken userToken) {
        tokenRepository.save(userToken);
    }

    public void deleteToken(String username) {
        tokenRepository.deleteById(username);
    }

    public boolean existUserToken(String username) {
        return tokenRepository.existsById(username);
    }
}
