package com.exam.catering.service;

import com.exam.catering.domain.Client;
import com.exam.catering.domain.Orders;
import com.exam.catering.repository.ClientRepository;
import com.exam.catering.repository.OrdersRepository;
import com.exam.catering.security.domain.SecurityCredentials;
import com.exam.catering.security.repository.SecurityCredentialsRepository;
import org.junit.jupiter.api.BeforeAll;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(MockitoExtension.class)
public class OrdersServiceTest {

    private static final Integer ID_ORDER = 10;

    private static final String NAME = "Дима";

    private static final Integer ID_CLIENT = 4;
    @InjectMocks
    private OrdersService ordersService;
    @Mock
    private OrdersRepository ordersRepository;
    @Mock
    private SecurityCredentialsRepository securityCredentialsRepository;
    @Mock
    private ClientRepository clientRepository;

    @BeforeAll
    public static void beforeAll() {
        Authentication authenticationMock = Mockito.mock(Authentication.class);
        SecurityContext securityContextMock = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);
        SecurityContextHolder.setContext(securityContextMock);
    }

    @Test
    public void getOrderTest() {
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
        Mockito.when(ordersRepository.findById(ID_ORDER)).thenReturn(Optional.of(order));
        ordersService.getOrder(ID_ORDER);
        Mockito.verify(ordersRepository, Mockito.times(1)).findById(anyInt());
    }

    @Test
    public void getOrdersTest() {
        Orders order = new Orders();
        List<Orders> ordersList = new ArrayList<>();
        ordersList.add(order);
        order.setId(ID_ORDER);
        Mockito.when(ordersRepository.findAll()).thenReturn(ordersList);
        ordersService.getOrders();
        Mockito.verify(ordersRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void createOrder() {
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
        Client client = new Client();
        client.setId(ID_CLIENT);
        Mockito.when(clientRepository.findById(ID_CLIENT)).thenReturn(Optional.of(client));
        Orders order = new Orders();
        order.setClient(new Client());
        order.setId(ID_ORDER);
        ordersService.createOrder(new Orders());
        Mockito.verify(ordersRepository, Mockito.times(1)).save(any());
    }

    @Test
    public void updateOrder() {
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
        Client client = new Client();
        client.setId(ID_CLIENT);
        Mockito.when(clientRepository.findById(ID_CLIENT)).thenReturn(Optional.of(client));
        Orders order = new Orders();
        order.setClient(new Client());
        order.setId(ID_ORDER);
        ordersService.updateOrder(new Orders());
        Mockito.verify(ordersRepository, Mockito.times(1)).saveAndFlush(any());
    }

    @Test
    public void deleteOrder() {
        ordersService.deleteOrderById(ID_ORDER);
        Mockito.verify(ordersRepository, Mockito.times(1)).deleteById(anyInt());
    }
}
