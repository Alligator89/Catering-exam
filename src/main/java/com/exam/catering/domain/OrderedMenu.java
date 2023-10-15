package com.exam.catering.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
@Entity(name = "ordered_menu")
@Schema(description = "description of orderedMenu")
public class OrderedMenu {
    @Schema(description = "it is unique orderedMenu`s id")
    @Id
    @SequenceGenerator(name = "orderedMenu_seq", sequenceName = "ordered_menu_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderedMenu_seq")
    @JsonBackReference
    private Integer id;

    @OneToMany
    private List<Dishes> dishes = new ArrayList<>();

    @ManyToOne
    @JsonBackReference(value = "orderedMenu-orders")
    private Orders orders;

    public void addDishes(Dishes dishes) {
        this.dishes.add(dishes);
    }

    public void clearOrderedMenu() {
        dishes.clear();
    }

}
