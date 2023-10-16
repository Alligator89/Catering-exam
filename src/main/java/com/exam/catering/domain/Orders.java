package com.exam.catering.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Component
@Schema(description = "description of order")
@Entity(name = "orders")
public class Orders {
    @Schema(description = "it is unique order`s id")
    @Id
    @SequenceGenerator(name = "orders_seq", sequenceName = "orders_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_seq")
    private Integer id;

    @FutureOrPresent
    @Column(name = "reserved_date", nullable = false)
    private LocalDateTime reservedDate;

    @Positive
    @Column(name = "count_of_person", nullable = false)
    private Long countOfPerson;

    @Column(name = "event", nullable = false)
    @Enumerated(EnumType.STRING)
    private Event event;

    @Positive
    @Column(name = "general_cost", nullable = false)
    private Long generalCost;

    @Column(name = "flag_is_paid", nullable = false)
    private Boolean isPaid = false;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @JsonBackReference
    private Client client;


    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "orderedMenu-orders")
    private List<OrderedMenu> orderedMenu;

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }

    public void payOrder(){
        isPaid = true;
    }

//     public void addOrderedMenu(OrderedMenu orderedMenu) {
//        this.orderedMenu.add(orderedMenu);
//    }
//
//    public void updateOrderedMenu(OrderedMenu orderedMenu) {
//        this.orderedMenu.add(orderedMenu);
//    }
}
