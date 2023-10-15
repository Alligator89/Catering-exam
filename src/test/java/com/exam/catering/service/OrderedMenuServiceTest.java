package com.exam.catering.service;

import com.exam.catering.domain.*;
import com.exam.catering.exceptions.OrderNotFoundException;
import com.exam.catering.exceptions.OrderedMenuNotFoundException;
import com.exam.catering.repository.OrderedMenuRepository;
import com.exam.catering.repository.OrdersRepository;
import com.exam.catering.security.domain.SecurityCredentials;
import com.exam.catering.security.repository.SecurityCredentialsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderedMenuServiceTest {

    private static final Integer ID_ORDERED_MENU = 16;

    private static final Integer ID_ORDER = 20;

    private static final String NAME = "Дима";

    private static final Integer ID_CLIENT = 2;

    @InjectMocks
    private OrderedMenuService orderedMenuService;

    @Mock
    private OrderedMenuRepository orderedMenuRepository;

    @Mock
    private OrdersRepository ordersRepository;

    @Mock
    private SecurityCredentialsRepository securityCredentialsRepository;

    @Test
    public void getOrderedMenuTest() {
        SecurityContextHolder.setContext(new SecurityContext() {
            @Override
            public Authentication getAuthentication() {
                return new Authentication() {
                    @Override
                    public Collection<? extends GrantedAuthority> getAuthorities() {
                        return null;
                    }

                    @Override
                    public Object getCredentials() {
                        return null;
                    }

                    @Override
                    public Object getDetails() {
                        return null;
                    }

                    @Override
                    public Object getPrincipal() {
                        return null;
                    }

                    @Override
                    public boolean isAuthenticated() {
                        return false;
                    }

                    @Override
                    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
                    }

                    @Override
                    public String getName() {
                        return NAME;
                    }
                };
            }

            @Override
            public void setAuthentication(Authentication authentication) {
            }
        });
        SecurityCredentials securityCredentials = new SecurityCredentials();
        securityCredentials.setClientId(ID_CLIENT);
        Mockito.when(securityCredentialsRepository.findByClientLogin(NAME)).thenReturn(Optional.of(securityCredentials));
        Orders order = new Orders();
        order.setClient(new Client());
        order.setId(ID_ORDER);
        OrderedMenu orderedMenu = new OrderedMenu();
        orderedMenu.setOrders(order);
        orderedMenu.setId(ID_ORDERED_MENU);
        Mockito.when(orderedMenuRepository.findById(ID_ORDERED_MENU)).thenReturn(Optional.of(orderedMenu));
        orderedMenuService.getOrderedMenu(ID_ORDERED_MENU);
        Mockito.verify(orderedMenuRepository, Mockito.times(1)).findById(anyInt());
    }

    @Test
    public void createOrderedMenu() {
        when(ordersRepository.findById(ID_ORDER))
                .thenReturn(Optional.empty());
        CreateMenuDTO createMenuDTO = new CreateMenuDTO();
        createMenuDTO.setOrderId(ID_ORDER);
        Assertions.assertThrows(OrderNotFoundException.class, () -> orderedMenuService.createOrderedMenu(createMenuDTO));
    }

    @Test
    public void updateOrderedMenu() {
        when(orderedMenuRepository.findById(ID_ORDERED_MENU))
                .thenReturn(Optional.empty());
        UpdateOrderedMenuDTO updateOrderedMenuDTO = new UpdateOrderedMenuDTO();
        updateOrderedMenuDTO.setOrderedMenuId(ID_ORDERED_MENU);
        Assertions.assertThrows(OrderedMenuNotFoundException.class, () -> orderedMenuService.updateOrderedMenu(updateOrderedMenuDTO));
    }

    @Test
    public void deleteOrderedMenu() {
        orderedMenuService.deleteOrderedMenu(ID_ORDERED_MENU);
        Mockito.verify(orderedMenuRepository, Mockito.times(1)).deleteById(anyInt());
    }
}
