package com.exam.catering.service;

import com.exam.catering.domain.Dishes;
import com.exam.catering.repository.DishRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(MockitoExtension.class)
public class DishServiceTest {

    private static final Integer ID_VALUE = 2;

    @InjectMocks
    private DishService dishService;

    @Mock
    private DishRepository dishRepository;

    @Test
    public void getDishesTest() {
        dishService.getList();
        Mockito.verify(dishRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void getDishTest() {
        dishService.getDish(ID_VALUE);
        Mockito.verify(dishRepository, Mockito.times(1)).findById(anyInt());
    }

    @Test
    public void createDish() {
        dishService.createDish(new Dishes());
        Mockito.verify(dishRepository, Mockito.times(1)).save(any());
    }

    @Test
    public void updateDish() {
        dishService.updateDish(new Dishes());
        Mockito.verify(dishRepository, Mockito.times(1)).saveAndFlush(any());
    }

    @Test
    public void deleteDish() {
        dishService.deleteDish(ID_VALUE);
        Mockito.verify(dishRepository, Mockito.times(1)).deleteById(anyInt());
    }
}

