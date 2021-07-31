package com.getir.readingisgood.controller;

import com.getir.readingisgood.model.response.GenericReturnValue;
import com.getir.readingisgood.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * @PostMapping("/login") API already implemented by Spring Security
     */

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/logout")
    public ResponseEntity<GenericReturnValue<Boolean>> logout() {
        return ResponseEntity.ok(authService.logout());
    }
}