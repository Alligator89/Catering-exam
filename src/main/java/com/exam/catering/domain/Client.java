package com.exam.catering.domain;

import lombok.Data;

import java.util.List;

@Data
public class Client {

    private Integer id;


    private String firstName;


    private String lastName;


    private String emailAddress;


    private String phoneNumber;


    private List<Orders> orders;
}

