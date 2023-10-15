package com.exam.catering.security.service;

import com.exam.catering.domain.Client;
import com.exam.catering.domain.Role;
import com.exam.catering.repository.ClientRepository;
import com.exam.catering.security.JwtUtils;
import com.exam.catering.security.domain.AuthRequest;
import com.exam.catering.security.domain.RegistrationDTO;
import com.exam.catering.security.domain.SecurityCredentials;
import com.exam.catering.security.repository.SecurityCredentialsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Component
public class SecurityService {
    private static final Logger log = LoggerFactory.getLogger(SecurityService.class);

    private final JwtUtils jwtUtils;

    private final PasswordEncoder passwordEncoder;

    private final Client client;

    private final SecurityCredentials securityCredentials;

    private final ClientRepository clientRepository;

    private final SecurityCredentialsRepository securityCredentialsRepository;

    public SecurityService(JwtUtils jwtUtils, PasswordEncoder passwordEncoder, Client client, SecurityCredentials securityCredentials, ClientRepository clientRepository, SecurityCredentialsRepository securityCredentialsRepository) {
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
        this.client = client;
        this.securityCredentials = securityCredentials;
        this.clientRepository = clientRepository;
        this.securityCredentialsRepository = securityCredentialsRepository;
    }

    public String generateToken(AuthRequest authRequest) {
        Optional<SecurityCredentials> credentials = securityCredentialsRepository.findByClientLogin(authRequest.getLogin());
        if (credentials.isPresent() && passwordEncoder.matches(authRequest.getPassword(), credentials.get().getClientPassword())) {
            log.info("Token is successfully generated!");
            return jwtUtils.generateJwtToken(authRequest.getLogin());
        }
        log.info("Token is not generated!");
        return "";
    }

    @Transactional(rollbackFor = Exception.class)
    public void registration (RegistrationDTO registrationDTO) {
        client.setFirstName(registrationDTO.getFirstName());
        client.setLastName(registrationDTO.getLastName());
        client.setEmailAddress(registrationDTO.getEmailAddress());
        client.setPhoneNumber(registrationDTO.getPhoneNumber());
        Client clientResult = clientRepository.save(client);

        securityCredentials.setClientLogin(registrationDTO.getClientLogin());
        securityCredentials.setClientPassword(passwordEncoder.encode(registrationDTO.getClientPassword()));
        securityCredentials.setClientRole(Role.USER);
        securityCredentials.setClientId(clientResult.getId());

        clientRepository.save(client);
        securityCredentialsRepository.save(securityCredentials);
    }
}
