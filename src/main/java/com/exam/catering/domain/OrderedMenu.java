package com.exam.catering.domain;

import lombok.Data;

import java.util.List;

@Data
public class OrderedMenu {

    private Integer id;

    private List<Dishes> dishes;

    private Orders orders;

}
