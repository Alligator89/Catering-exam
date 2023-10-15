package com.exam.catering.service;

import com.exam.catering.domain.Client;
import com.exam.catering.domain.Orders;
import com.exam.catering.repository.ClientRepository;
import com.exam.catering.repository.OrdersRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrdersService {

    private final OrdersRepository ordersRepository;

    private final ClientRepository clientRepository;

    private final SecurityCredentialsRepository securityCredentialsRepository;

    public OrdersService(OrdersRepository ordersRepository, ClientRepository clientRepository, SecurityCredentialsRepository securityCredentialsRepository) {
        this.ordersRepository = ordersRepository;
        this.clientRepository = clientRepository;
        this.securityCredentialsRepository = securityCredentialsRepository;
    }

    public List<Orders> getOrders() {
        List<Orders> ordersList = ordersRepository.findAll();
        if (ordersList.isEmpty()) {
            throw new ListOfOrdersNotFoundException();
        }
        return ordersList;
    }

    public Optional<Orders> getOrder(Integer id) {
        Optional<Orders> order = ordersRepository.findById(id);
        if (order.isEmpty()) {
            throw new OrderNotFoundException();
        }
        Integer clientId = order.get().getClient().getId();
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<SecurityCredentials> securityCredentials = securityCredentialsRepository.findByClientLogin(login);
        if (securityCredentials.isPresent()) {
            Integer securityCredentialsId = securityCredentials.get().getClientId();
            if (securityCredentialsId.equals(clientId)) {
                return order;
            }
            return Optional.empty();
        }
        return Optional.empty();
    }

    public void createOrder(Orders order) {
        order.setEvent(order.getEvent());
        order.setReservedDate(order.getReservedDate());
        order.setCountOfPerson(order.getCountOfPerson());
        order.setGeneralCost(order.getGeneralCost());
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<SecurityCredentials> clientLogin = securityCredentialsRepository.findByClientLogin(login);
        if (clientLogin.isEmpty()) {
            throw new ClientNotFoundException();
        }
        Optional<Client> clientId = clientRepository.findById(clientLogin.get().getClientId());
        clientId.ifPresent(order::setClient);
        ordersRepository.save(order);
    }

    public void updateOrder(Orders order) {
        order.setId(order.getId());
        order.setEvent(order.getEvent());
        order.setReservedDate(order.getReservedDate());
        order.setCountOfPerson(order.getCountOfPerson());
        order.setGeneralCost(order.getGeneralCost());
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<SecurityCredentials> clientLogin = securityCredentialsRepository.findByClientLogin(login);
        if (clientLogin.isEmpty()) {
            throw new ClientNotFoundException();
        }
        Optional<Client> clientId = clientRepository.findById(clientLogin.get().getClientId());
        clientId.ifPresent(order::setClient);
        ordersRepository.saveAndFlush(order);
    }

    public void deleteOrderById(Integer id) {
        ordersRepository.deleteById(id);
    }
}
