package com.exam.catering.service;

import com.exam.catering.domain.*;
import com.exam.catering.exceptions.DishNotFoundException;
import com.exam.catering.exceptions.OrderNotFoundException;
import com.exam.catering.exceptions.OrderedMenuNotFoundException;
import com.exam.catering.repository.DishRepository;
import com.exam.catering.repository.OrderedMenuRepository;
import com.exam.catering.repository.OrdersRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderedMenuService {

    private final OrderedMenuRepository orderedMenuRepository;

    private final OrdersRepository ordersRepository;

    private final DishRepository dishRepository;

    private final SecurityCredentialsRepository securityCredentialsRepository;

    public OrderedMenuService(OrderedMenuRepository orderedMenuRepository, OrdersRepository ordersRepository, DishRepository dishRepository, SecurityCredentialsRepository securityCredentialsRepository) {
        this.orderedMenuRepository = orderedMenuRepository;
        this.ordersRepository = ordersRepository;
        this.dishRepository = dishRepository;
        this.securityCredentialsRepository = securityCredentialsRepository;
    }

    public Optional<OrderedMenu> getOrderedMenu(Integer id) {
        Optional<OrderedMenu> orderedMenu = orderedMenuRepository.findById(id);
        if (orderedMenu.isPresent()) {
            Integer clientId = orderedMenu.get().getOrders().getClient().getId();
            String login = SecurityContextHolder.getContext().getAuthentication().getName();
            Optional<SecurityCredentials> securityCredentials = securityCredentialsRepository.findByClientLogin(login);
            if (securityCredentials.isPresent()) {
                Integer securityCredentialsId = securityCredentials.get().getClientId();
                if (securityCredentialsId.equals(clientId)) {
                    return orderedMenu;
                }
                return Optional.empty();
            }
            return Optional.empty();
        }
        return Optional.empty();
    }

    public void createOrderedMenu(CreateMenuDTO createMenuDTO) {
        Optional<Orders> order = ordersRepository.findById(createMenuDTO.getOrderId());
        if (order.isEmpty()) {
            throw new OrderNotFoundException();
        }
        OrderedMenu orderedMenu = new OrderedMenu();
        orderedMenu.setOrders(order.get());
        List<Integer> dishesIds = createMenuDTO.getDishesIds();
        for (Integer dishId : dishesIds) {
            Optional<Dishes> dish = dishRepository.findById(dishId);
            if (dish.isEmpty()) {
                throw new DishNotFoundException();
            }
            orderedMenu.addDishes(dish.get());
        }
        List<Dishes> dishes = orderedMenu.getDishes();
        long sum = 0;
        for (Dishes dish : dishes) {
            Long cost = dish.getCost();
            sum += cost;
        }
        orderedMenu.getOrders().setGeneralCost(sum);
        orderedMenuRepository.save(orderedMenu);
    }

    public Long updateGeneralCost(List<Dishes> dishes) {
        long sum = 0;
        for (Dishes dish : dishes) {
            Long cost = dish.getCost();
            sum += cost;
        }
        return sum;
    }

    public void updateOrderedMenu(UpdateOrderedMenuDTO updateOrderedMenuDTO) {
        Optional<OrderedMenu> orderedMenu = orderedMenuRepository.findById(updateOrderedMenuDTO.getOrderedMenuId());
        if (orderedMenu.isEmpty()) {
            throw new OrderedMenuNotFoundException();
        }
        OrderedMenu orderedMenuSaved = orderedMenu.get();
        orderedMenuSaved.clearOrderedMenu();
        List<Integer> dishesIds = updateOrderedMenuDTO.getDishesIds();
        for (Integer dishId : dishesIds) {
            Optional<Dishes> dish = dishRepository.findById(dishId);
            if (dish.isEmpty()) {
                throw new DishNotFoundException();
            }
            orderedMenuSaved.addDishes(dish.get());
        }
        orderedMenuSaved.getOrders().setGeneralCost(updateGeneralCost(orderedMenuSaved.getDishes()));
        orderedMenuRepository.saveAndFlush(orderedMenuSaved);
    }

    public void deleteOrderedMenu(Integer id) {
        orderedMenuRepository.deleteById(id);
    }
}