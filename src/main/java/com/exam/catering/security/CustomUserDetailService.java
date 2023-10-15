package com.exam.catering.security;

import com.exam.catering.security.domain.SecurityCredentials;
import com.exam.catering.security.repository.SecurityCredentialsRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final SecurityCredentialsRepository securityCredentialsRepository;

    public CustomUserDetailService(SecurityCredentialsRepository securityCredentialsRepository) {
        this.securityCredentialsRepository = securityCredentialsRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<SecurityCredentials> securityCredentials = securityCredentialsRepository.findByClientLogin(username);
        if (securityCredentials.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        return User
                .withUsername(securityCredentials.get().getClientLogin())
                .password(securityCredentials.get().getClientPassword())
                .roles(securityCredentials.get().getClientRole().toString())
                .build();
    }
}
