package com.samu.todoapi.service;

import com.samu.todoapi.security.UserPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.samu.todoapi.entity.User;
import com.samu.todoapi.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
 
    UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmailIgnoreCase(username)
                                    .orElseThrow(() -> new UsernameNotFoundException("Usuário " + username + " não encontrado!"));

        return new UserPrincipal(user);
    }
}
