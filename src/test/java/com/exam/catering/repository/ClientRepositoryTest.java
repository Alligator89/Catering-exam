package com.exam.catering.repository;


import com.exam.catering.domain.Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ClientRepositoryTest {

    @Autowired
    ClientRepository clientRepository;

    static Client client = new Client();

    static List<Client> clientList = new ArrayList<>();

    private static final Integer ID_CLIENT = 3;

    @BeforeAll
    static void beforeAll() {
        client.setId(ID_CLIENT);
        client.setFirstName("Юра");
        client.setLastName("Петров");
        client.setEmailAddress("yura@yandex.by");
        client.setPhoneNumber("8(029)987-56-56");
        clientList.add(client);
    }

    @Test
    void findAllTest() {
        clientRepository.save(client);
        List<Client> newList = clientRepository.findAll();
        Assertions.assertNotNull(newList);
    }

    @Test
    void findByIdTest() {
        Client saved = clientRepository.save(client);
        Optional<Client> newClient = clientRepository.findById(saved.getId());
        Assertions.assertTrue(newClient.isPresent());
    }

    @Test
    void findByLastNameTest() {
        Client saved = clientRepository.save(client);
        Optional<Client> newClient = clientRepository.findClientByLastName(saved.getLastName());
        Assertions.assertTrue(newClient.isPresent());
    }

    @Test
    void saveTest() {
        List<Client> oldList = clientRepository.findAll();
        clientRepository.save(client);
        List<Client> newList = clientRepository.findAll();
        Assertions.assertNotEquals(oldList.size(), newList.size());
    }

    @Test
    void updateTest() {
        Client clientSaved = clientRepository.save(client);
        clientSaved.setFirstName("Дима");
        Client clientUpdated = clientRepository.saveAndFlush(clientSaved);
        Assertions.assertEquals(clientUpdated.getFirstName(), "Дима");
    }

    @Test
    void deleteTest() {
        Client clientSaved = clientRepository.save(client);
        clientRepository.delete(clientSaved);
        Optional<Client> client = clientRepository.findById(clientSaved.getId());
        Assertions.assertFalse(client.isPresent());
    }
}