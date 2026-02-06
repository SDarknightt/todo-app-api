package com.samu.todoapi.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.samu.todoapi.dto.UserAuthenticatedDTO;
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
        User user = userRepository.findByEmail(username)
                                    .orElseThrow(() -> new UsernameNotFoundException("Usuário " + username + " não encontrado!"));

        return new UserAuthenticatedDTO(
            user.getName(),
            user.getPassword(),
            List.of(user.getAuthority())
        );
    }
}
