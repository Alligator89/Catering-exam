package com.exam.catering.controller;

import com.exam.catering.domain.Dishes;
import com.exam.catering.security.filter.JwtAuthenticationFilter;
import com.exam.catering.service.DishService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = DishesController.class)
@AutoConfigureMockMvc(addFilters = false)
public class DishesControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    DishService dishService;

    static List<Dishes> dishList = new ArrayList<>();

    static Dishes dish = new Dishes();

    private static final Integer ID_VALUE = 2;

    @BeforeAll
    public static void beforeAll() {
        dish.setId(ID_VALUE);
        dish.setName("Брускетта с форелью(Форель, Сливочный сыр, Багет, Листья салата)");
        dish.setWeight(50L);
        dish.setCost(4L);
        dishList.add(dish);
    }

    @Test
    public void getDishTest() throws Exception {
        Mockito.when(dishService.getDish(anyInt())).thenReturn(Optional.ofNullable(dish));
        mockMvc.perform(get("/dishes/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Брускетта с форелью(Форель, Сливочный сыр, Багет, Листья салата)"));
    }

    @Test
    public void getDishesTestReturnListOfDishes() throws Exception {
        Mockito.when(dishService.getList()).thenReturn(dishList);
        mockMvc.perform(get("/dishes/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].name", Matchers.is("Брускетта с форелью(Форель, Сливочный сыр, Багет, Листья салата)")));
    }

    @Test
    public void createDishTest() throws Exception {
        DishService mockDS = Mockito.mock(DishService.class);
        Mockito.doNothing().when(mockDS).createDish(any());

        mockMvc.perform(post("/dishes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dish)))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateDishTest() throws Exception {
        DishService mockDS = Mockito.mock(DishService.class);
        Mockito.doNothing().when(mockDS).updateDish(any());

        mockMvc.perform(put("/dishes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dish)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteDishTest() throws Exception {
        DishService mockDS = Mockito.mock(DishService.class);
        Mockito.doNothing().when(mockDS).deleteDish(anyInt());

        mockMvc.perform(delete("/dishes/2"))
                .andExpect(status().isNoContent());
    }
}
