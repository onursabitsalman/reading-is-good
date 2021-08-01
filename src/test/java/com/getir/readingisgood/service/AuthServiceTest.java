package com.getir.readingisgood.service;

import com.getir.readingisgood.exceptions.ResourceNotFoundException;
import com.getir.readingisgood.repository.UserTokenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserTokenService userTokenService;

    @Mock
    private UserTokenRepository tokenRepository;

    @Test
    public void logout_successfully() {
        permitAuthentication(true);
        authService.logout();
    }

    @Test
    public void logout_withoutLogin_expectedResourceNotFoundException() {
        permitAuthentication(false);
        assertThrows(ResourceNotFoundException.class, () -> authService.logout());
    }

    private void permitAuthentication(boolean permitAuthentication) {

        SecurityContext securityContext = new SecurityContext() {
            @Override
            public Authentication getAuthentication() {
                if (permitAuthentication)
                    return new Authentication() {
                        @Override
                        public Collection<? extends GrantedAuthority> getAuthorities() {
                            return null;
                        }

                        @Override
                        public Object getCredentials() {
                            return null;
                        }

                        @Override
                        public Object getDetails() {
                            return null;
                        }

                        @Override
                        public Object getPrincipal() {
                            return null;
                        }

                        @Override
                        public boolean isAuthenticated() {
                            return true;
                        }

                        @Override
                        public void setAuthenticated(boolean b) throws IllegalArgumentException {
                        }

                        @Override
                        public String getName() {
                            return "ADMIN";
                        }
                    };
                else
                    return null;
            }

            @Override
            public void setAuthentication(Authentication authentication) {
            }
        };
        SecurityContextHolder.setContext(securityContext);
    }

}