package com.exam.catering.security.domain;

import com.exam.catering.domain.Role;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Entity(name = "security_credentials_clients")
@Component
public class SecurityCredentials {
    @Id
    @SequenceGenerator(name = "securityCredentialsSeq", sequenceName = "security_credentials_clients_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "securityCredentialsSeq")
    private Integer id;

    @Column(name = "client_login")
    private String clientLogin;

    @Column(name = "client_password")
    private String clientPassword;

    @Column(name = "client_role")
    @Enumerated(EnumType.STRING)
    private Role clientRole;

    @Column(name = "client_id")
    private Integer clientId;
}
