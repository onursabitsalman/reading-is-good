package com.getir.readingisgood.security.service;

import com.getir.readingisgood.entity.User;
import com.getir.readingisgood.security.UserDetailsDao;
import com.getir.readingisgood.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsDaoService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetailsDao loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByUserName(username);
        return UserDetailsDao.builder().username(user.getUsername()).password(user.getPassword()).id(user.getId())
                .authorities(user.getRoleType().getPermissions())
                .isAccountNonExpired(true).isAccountNonLocked(true)
                .isCredentialsNonExpired(true).isEnabled(true).build();
    }

    public Long getUserId() throws UsernameNotFoundException {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDetailsDao userDetailsDao = loadUserByUsername(currentUsername);
        return userDetailsDao.getId();
    }
}
