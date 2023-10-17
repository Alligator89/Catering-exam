package com.exam.catering.controller;

import com.exam.catering.domain.Client;
import com.exam.catering.security.filter.JwtAuthenticationFilter;
import com.exam.catering.service.ClientService;
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
@WebMvcTest(value = ClientController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ClientControllerTest {

    private static final String SURNAME = "Любенков";

    private static final String NAME = "Денис";

    @MockBean
    ClientService clientService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private ObjectMapper objectMapper;

    static List<Client> clientList = new ArrayList<>();

    static Client client = new Client();

    @Autowired
    MockMvc mockMvc;

    @BeforeAll
    public static void beforeAll() {
        client.setId(2);
        client.setFirstName("Денис");
        client.setLastName("Любенков");
        client.setEmailAddress("denisliubenkov@yandex.by");
        client.setPhoneNumber("8(029)272-41-11");
        clientList.add(client);
    }

    @Test
    public void getClientTest() throws Exception {
        Mockito.when(clientService.getOneClient(anyInt())).thenReturn(Optional.ofNullable(client));
        mockMvc.perform(get("/client/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(NAME));
    }

    @Test
    public void getClientsTestReturnCollectionsOfClients() throws Exception {
        Mockito.when(clientService.getClients()).thenReturn(clientList);
        mockMvc.perform(get("/client"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].firstName", Matchers.is(NAME)));
    }

    @Test
    public void getClientTestReturnClientByLastName() throws Exception {
        Mockito.when(clientService.findByLastName(SURNAME)).thenReturn(Optional.ofNullable(client));
        mockMvc.perform(get("/client/lastName/Любенков"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName").value(SURNAME));
    }

    @Test
    public void createClientTest() throws Exception {
        ClientService mockCS = Mockito.mock(ClientService.class);
        Mockito.doNothing().when(mockCS).createClient(any());
        mockMvc.perform(post("/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateClientTest() throws Exception {
        ClientService mockCS = Mockito.mock(ClientService.class);
        Mockito.doNothing().when(mockCS).updateClient(any());
        mockMvc.perform(put("/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteClientTest() throws Exception {
        ClientService mockCS = Mockito.mock(ClientService.class);
        Mockito.doNothing().when(mockCS).deleteClientById(anyInt());

        mockMvc.perform(delete("/client/2"))
                .andExpect(status().isNoContent());
    }
}