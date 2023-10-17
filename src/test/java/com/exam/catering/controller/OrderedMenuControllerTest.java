package com.exam.catering.controller;

import com.exam.catering.domain.OrderedMenu;
import com.exam.catering.security.filter.JwtAuthenticationFilter;
import com.exam.catering.service.OrderedMenuService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = OrderedMenuController.class)
@AutoConfigureMockMvc(addFilters = false)
public class OrderedMenuControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderedMenuService orderedMenuService;

    static List<OrderedMenu> orderedMenuList = new ArrayList<>();

    static OrderedMenu orderedMenu = new OrderedMenu();

    @BeforeAll
    public static void beforeAll() {
        orderedMenu.setId(20);
        orderedMenuList.add(orderedMenu);
    }

    @Test
    public void getOrderedMenuTest() throws Exception {
        when(orderedMenuService.getOrderedMenu(anyInt())).thenReturn(Optional.ofNullable(orderedMenu));
        mockMvc.perform(get("/orderedMenu/20"))
                .andExpect(status().isOk());
    }

    @Test
    public void createOrderedMenuTest() throws Exception {
        OrderedMenuService mockOMS = mock(OrderedMenuService.class);
        Mockito.doNothing().when(mockOMS).createOrderedMenu(any());
        mockMvc.perform(post("/orderedMenu/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderedMenu)))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateOrderedMenuTest() throws Exception {
        OrderedMenuService mockOMS = mock(OrderedMenuService.class);
        Mockito.doNothing().when(mockOMS).updateOrderedMenu(any());
        mockMvc.perform(put("/orderedMenu")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderedMenu)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteOrderedMenuTest() throws Exception {
        OrderedMenuService mockOMS = mock(OrderedMenuService.class);
        Mockito.doNothing().when(mockOMS).deleteOrderedMenu(anyInt());
        mockMvc.perform(delete("/orderedMenu/2"))
                .andExpect(status().isNoContent());
    }
}
