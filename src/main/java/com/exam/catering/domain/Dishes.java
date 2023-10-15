package com.exam.catering.domain;

import lombok.Data;

@Data
public class Dishes {

    private Integer id;

    private String name;

    private Long weight;

    private Long cost;

    private TypeOfDish typeOfDish;
}
