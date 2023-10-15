package com.exam.catering.domain;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Orders {

    private Integer id;

    private LocalDateTime reservedDate;

    private Long countOfPerson;

    private Event event;

    private Long generalCost;

    private Boolean isPaid;

    private Client client;

    private List<OrderedMenu> orderedMenu;

}
