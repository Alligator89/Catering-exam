package com.exam.catering.security.domain;

import com.exam.catering.domain.Role;
import lombok.Data;

@Data
public class SecurityCredentials {

    private Integer id;

    private String clientLogin;

    private String clientPassword;

    private Role clientRole;

    private Integer clientId;
}
