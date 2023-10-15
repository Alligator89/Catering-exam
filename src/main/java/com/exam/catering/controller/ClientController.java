package com.exam.catering.controller;

import com.exam.catering.domain.Client;
import com.exam.catering.exceptions.ClientNotFoundException;
import com.exam.catering.service.ClientService;
import io.swagger.v3.oas.annotations.Parameter;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
@SecurityRequirement(name = "Bearer Authentication")
public class ClientController {
    private static final Logger log = LoggerFactory.getLogger(ClientController.class);

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = clientService.getClients();
        if (clients.isEmpty()) {
            log.info("Clients are not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            log.info("Clients are found!");
            return new ResponseEntity<>(clients, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getOneClient(@PathVariable @Parameter(description = "it is client`s id") Integer id) {
        Client client = clientService.getOneClient(id).orElseThrow(ClientNotFoundException::new);
        log.info("Client with id: " + id + " is found!");
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @GetMapping("/lastName/{lastName}")
    public ResponseEntity<Client> getClientByLastName(@PathVariable String lastName) {
        Client client = clientService.findByLastName(lastName).orElseThrow(ClientNotFoundException::new);
        log.info("Client with lastname " + lastName + " is found!");
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createClient(@RequestBody Client client) {
        clientService.createClient(client);
        log.info("Client with firstname " + client.getFirstName() + " is created!");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<HttpStatus> updateClient(@RequestBody Client client) {
        clientService.updateClient(client);
        log.info("Client with id: " + client.getId() + " is updated!");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteClient(@PathVariable Integer id) {
        clientService.deleteClientById(id);
        log.info("Client with id: " + id + " is deleted!");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
