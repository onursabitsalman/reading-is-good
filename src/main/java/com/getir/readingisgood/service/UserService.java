package com.getir.readingisgood.service;

import com.getir.readingisgood.entity.User;
import com.getir.readingisgood.exceptions.ResourceNotFoundException;
import com.getir.readingisgood.repository.UserRepository;
import com.getir.readingisgood.utils.ErrorMessages;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User getUserByUserName(@NonNull String userName) {
        return userRepository.findByUsername(userName)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND));
    }
}