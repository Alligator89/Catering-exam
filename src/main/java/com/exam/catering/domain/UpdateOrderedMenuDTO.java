package com.exam.catering.domain;

import lombok.Data;

import java.util.List;

@Data
public class UpdateOrderedMenuDTO {

    private Integer orderedMenuId;

    private List<Integer> dishesIds;
}
