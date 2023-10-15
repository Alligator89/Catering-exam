package com.exam.catering.security.domain;

import lombok.Data;

@Data
public class RegistrationDTO {

    private String firstName;

    private String lastName;

    private String emailAddress;

    private String phoneNumber;

    private String clientLogin;

    private String clientPassword;
}
