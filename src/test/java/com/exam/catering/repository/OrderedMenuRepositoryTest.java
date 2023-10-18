package com.exam.catering.repository;

import com.exam.catering.domain.OrderedMenu;
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
public class OrderedMenuRepositoryTest {

    @Autowired
    OrderedMenuRepository orderedMenuRepository;

    static OrderedMenu orderedMenu = new OrderedMenu();

    @BeforeAll
    static void beforeAll() {
        orderedMenu.setId(20);
    }

    @Test
    void findByIdTest() {
        OrderedMenu saved = orderedMenuRepository.save(orderedMenu);
        Optional<OrderedMenu> newOrderedMenu = orderedMenuRepository.findById(saved.getId());
        Assertions.assertTrue(newOrderedMenu.isPresent());
    }

    @Test
    void saveTest() {
        List<OrderedMenu> oldList = orderedMenuRepository.findAll();
        orderedMenuRepository.save(orderedMenu);
        List<OrderedMenu> newList = orderedMenuRepository.findAll();
        Assertions.assertNotEquals(oldList.size(), newList.size());
    }

    @Test
    void updateTest() {
        OrderedMenu orderedMenuSaved = orderedMenuRepository.save(orderedMenu);
        OrderedMenu orderedMenuUpdated = orderedMenuRepository.saveAndFlush(orderedMenuSaved);
        Assertions.assertEquals(orderedMenuSaved.getId(), orderedMenuUpdated.getId());
    }

    @Test
    void deleteTest() {
        OrderedMenu orderedMenuSaved = orderedMenuRepository.save(orderedMenu);
        orderedMenuRepository.delete(orderedMenuSaved);
        Optional<OrderedMenu> orderedMenu = orderedMenuRepository.findById(orderedMenuSaved.getId());
        Assertions.assertFalse(orderedMenu.isPresent());
    }
}
