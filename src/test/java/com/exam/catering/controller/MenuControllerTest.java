package com.exam.catering.controller;

import com.exam.catering.domain.Menu;
import com.exam.catering.security.filter.JwtAuthenticationFilter;
import com.exam.catering.service.MenuService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = MenuController.class)
@AutoConfigureMockMvc(addFilters = false)
public class MenuControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    MenuService menuService;

    static List<Menu> menuList = new ArrayList<>();

    static Menu menu = new Menu();

    @BeforeAll
    public static void beforeAll() {
        menu.setDishList(menu.getDishList());
        menuList.add(menu);
    }

    @Test
    public void getMenuTest() throws Exception {
        Mockito.when(menuService.getMenu()).thenReturn(menu);
        mockMvc.perform(get("/menu"))
                .andExpect(status().isOk());
    }
}
