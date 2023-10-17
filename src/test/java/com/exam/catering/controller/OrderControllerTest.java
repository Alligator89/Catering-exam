package com.exam.catering.controller;

import com.exam.catering.domain.Orders;
import com.exam.catering.security.filter.JwtAuthenticationFilter;
import com.exam.catering.service.OrdersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.*;

import static com.exam.catering.domain.Event.WEDDING;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = OrderController.class)
@AutoConfigureMockMvc(addFilters = false)
public class OrderControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    OrdersService ordersService;

    static List<Orders> ordersList = new ArrayList<>();

    static Orders order = new Orders();

    private static final Integer ID_ORDER = 2;

    @BeforeAll
    public static void beforeAll() {
        order.setId(ID_ORDER);
        order.setEvent(WEDDING);
        order.setReservedDate(LocalDateTime.parse("2023-10-16T18:59:20"));
        order.setCountOfPerson(334L);
        order.setGeneralCost(44L);
        ordersList.add(order);
    }

    @Test
    public void getOrderTest() throws Exception {
        Mockito.when(ordersService.getOrder(anyInt())).thenReturn(Optional.ofNullable(order));
        mockMvc.perform(get("/orders/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.generalCost").value(44));
    }

    @Test
    public void getOrdersTestReturnListOfOrders() throws Exception {
        Mockito.when(ordersService.getOrders()).thenReturn(ordersList);
        mockMvc.perform(get("/orders/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].generalCost", Matchers.is(44)));
    }

    @Test
    public void createOrderTest() throws Exception {
        OrdersService mockOS = Mockito.mock(OrdersService.class);
        Mockito.doNothing().when(mockOS).createOrder(any());
        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateOrderTest() throws Exception {
        OrdersService mockOS = Mockito.mock(OrdersService.class);
        Mockito.doNothing().when(mockOS).updateOrder(any());
        mockMvc.perform(put("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteOrderTest() throws Exception {
        OrdersService mockOS = Mockito.mock(OrdersService.class);
        Mockito.doNothing().when(mockOS).deleteOrderById(anyInt());
        mockMvc.perform(delete("/orders/10"))
                .andExpect(status().isNoContent());
    }
}
