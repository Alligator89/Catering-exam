package com.exam.catering.domain;

import lombok.Data;

import java.util.List;

@Data
public class Menu {

    private List<Dishes> dishList;
}
