package com.exam.catering.domain;

import lombok.Data;

import java.util.List;

@Data
public class CreateMenuDTO {

    private Integer orderId;

    private List<Integer> dishesIds;
}
