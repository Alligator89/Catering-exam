package com.exam.catering.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Entity(name = "dishes")
@Schema(description = "description of dish")
public class Dishes {
    @Schema(description = "it`s unique dish`s id")
    @Id
    @SequenceGenerator(name = "dishes_seq", sequenceName = "dishes_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dishes_seq")
    @JsonBackReference
    private Integer id;

    @Pattern(regexp = "^[А-Яа-яЁё]+$")
    @Column(name = "name", nullable = false)
    private String name;

    @Positive
    @Column(name = "weight", nullable = false)
    private Long weight;

    @Positive
    @Column(name = "cost", nullable = false)
    private Long cost;

    @Column(name = "type_of_dishes", nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeOfDish typeOfDish;
}
