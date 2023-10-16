package com.exam.catering.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Schema(description = "description of client")
@Component
@Entity(name = "clients")
public class Client {
    @Schema(description = "it is unique client`s id")
    @Id
    @SequenceGenerator(name = "clients_seq", sequenceName = "clients_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clients_seq")
    private Integer id;

    @Pattern(regexp = "^[А-Яа-яЁё]+$")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Pattern(regexp = "^[А-Яа-яЁё]+$")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Email
    @Column(name = "email_address")
    private String emailAddress;

    @NotBlank
    @Pattern(regexp = "8[(]?[0-9]{3}[)]?[-\\s.]?[0-9]{3}[-\\s.]?[0-9]{2}?[-\\s.]?[0-9]{2}")
    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Orders> orders = new ArrayList<>();
}

