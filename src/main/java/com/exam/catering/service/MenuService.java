package com.exam.catering.service;

import com.exam.catering.domain.Menu;
import com.exam.catering.repository.DishRepository;
import org.springframework.stereotype.Service;

@Service
public class MenuService {

    private final DishRepository dishRepository;

    public MenuService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    public Menu getMenu() {
        Menu menu = new Menu();
        menu.setDishList(dishRepository.findAll());
        return menu;
    }
}
