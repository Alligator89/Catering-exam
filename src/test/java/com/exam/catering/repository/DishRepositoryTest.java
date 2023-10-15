package com.exam.catering.repository;

import com.exam.catering.domain.Dishes;
import com.exam.catering.domain.TypeOfDish;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DishRepositoryTest {

    @Autowired
    DishRepository dishRepository;

    static Dishes dish = new Dishes();

    private static final Integer ID_VALUE = 25;

    @BeforeAll
    static void beforeAll() {
        dish.setId(ID_VALUE);
        dish.setName("Жульен");
        dish.setWeight(1L);
        dish.setCost(5L);
        dish.setTypeOfDish(TypeOfDish.COLD_SNACKS);
    }

    @Test
    void findAllTest() {
        dishRepository.save(dish);
        List<Dishes> dishList = dishRepository.findAll();
        Assertions.assertNotNull(dishList);
    }

    @Test
    void findByIdTest() {
        Dishes saved = dishRepository.save(dish);
        Optional<Dishes> newDish = dishRepository.findById(saved.getId());
        Assertions.assertTrue(newDish.isPresent());
    }

    @Test
    void saveTest() {
        List<Dishes> oldList = dishRepository.findAll();
        dishRepository.save(dish);
        List<Dishes> newList = dishRepository.findAll();
        Assertions.assertNotEquals(oldList.size(), newList.size());
    }

    @Test
    void updateTest() {
        Dishes dishSaved = dishRepository.save(dish);
        dishSaved.setName("Жульен");
        Dishes dishUpdated = dishRepository.saveAndFlush(dishSaved);
        Assertions.assertEquals(dishUpdated.getName(), "Жульен");
    }

    @Test
    void deleteTest() {
        Dishes dishSaved = dishRepository.save(dish);
        dishRepository.delete(dishSaved);
        Optional<Dishes> dish = dishRepository.findById(dishSaved.getId());
        Assertions.assertFalse(dish.isPresent());
    }
}
