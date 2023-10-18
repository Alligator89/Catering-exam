package com.exam.catering.repository;

import com.exam.catering.domain.Event;
import com.exam.catering.domain.Orders;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrdersRepositoryTest {

    @Autowired
    OrdersRepository ordersRepository;


    private static final Integer ID_VALUE = 3;
    static Orders order = new Orders();

    @BeforeAll
    static void beforeAll() {
        String date = "2023-10-30T22:39:47";
        order.setId(ID_VALUE);
        order.setGeneralCost(56L);
        order.setEvent(Event.WEDDING);
        order.setCountOfPerson(45L);
        order.setReservedDate(LocalDateTime.parse(date));
    }

    @Test
    void findAllTest() {
        ordersRepository.save(order);
        List<Orders> newList = ordersRepository.findAll();
        Assertions.assertNotNull(newList);
    }

    @Test
    void findByIdTest() {
        Orders saved = ordersRepository.save(order);
        Optional<Orders> newOrder = ordersRepository.findById(saved.getId());
        Assertions.assertTrue(newOrder.isPresent());
    }

    @Test
    void saveTest() {
        List<Orders> oldList = ordersRepository.findAll();
        ordersRepository.save(order);
        List<Orders> newList = ordersRepository.findAll();
        Assertions.assertNotEquals(oldList.size(), newList.size());
    }

    @Test
    void updateTest() {
        Orders orderSaved = ordersRepository.save(order);
        orderSaved.setEvent(Event.WEDDING);
        Orders orderUpdated = ordersRepository.saveAndFlush(orderSaved);
        Assertions.assertEquals(orderUpdated.getEvent(), Event.WEDDING);
    }

    @Test
    void deleteTest() {
        Orders orderSaved = ordersRepository.save(order);
        ordersRepository.delete(orderSaved);
        Optional<Orders> order = ordersRepository.findById(orderSaved.getId());
        Assertions.assertFalse(order.isPresent());
    }
}
