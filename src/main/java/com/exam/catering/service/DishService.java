package com.exam.catering.service;

import com.exam.catering.domain.Dishes;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DishService {

    private final DishRepository dishRepository;

    public DishService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    public List<Dishes> getList() {
        return dishRepository.findAll();
    }

    public Optional<Dishes> getDish(Integer id) {
        return dishRepository.findById(id);
    }

    public void createDish(Dishes dish) {
        dish.setName(dish.getName());
        dish.setWeight(dish.getWeight());
        dish.setCost(dish.getCost());
        dishRepository.save(dish);
    }

    public void updateDish(Dishes dish) {
        dish.setId(dish.getId());
        dish.setName(dish.getName());
        dish.setWeight(dish.getWeight());
        dish.setCost(dish.getCost());
        dishRepository.saveAndFlush(dish);
    }

    public void deleteDish(Integer id) {
        dishRepository.deleteById(id);
    }
}
