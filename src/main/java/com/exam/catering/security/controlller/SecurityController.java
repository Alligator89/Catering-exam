package com.exam.catering.security.controlller;

import com.exam.catering.security.domain.AuthRequest;
import com.exam.catering.security.domain.AuthResponse;
import com.exam.catering.security.domain.RegistrationDTO;
import com.exam.catering.security.service.SecurityService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {
    private static final Logger log = LoggerFactory.getLogger(SecurityController.class);

    private final SecurityService securityService;

    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registration(@Valid @RequestBody RegistrationDTO registrationDTO) {
        securityService.registration(registrationDTO);
        log.info("Client " + registrationDTO.getFirstName() + " is successfully registered!");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/authentication")
    public ResponseEntity<AuthResponse> generateToken(@RequestBody AuthRequest authRequest) {
        String token = securityService.generateToken(authRequest);
        if (token.isBlank()) {
            log.info("Client " + authRequest.getLogin() + " is authenticated unsuccessfully!");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        log.info("Client " + authRequest.getLogin() + " is successfully authenticated!");
        return new ResponseEntity<>(new AuthResponse(token), HttpStatus.OK);
    }
}
