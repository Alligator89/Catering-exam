package com.exam.catering.controller;

import com.exam.catering.domain.Client;
import com.exam.catering.service.ClientService;
import io.swagger.v3.oas.annotations.Parameter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = clientService.getClients();
        if (clients.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(clients, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getOneClient(@PathVariable @Parameter(description = "it is client`s id") Integer id) {
        Client client = clientService.getOneClient(id).orElseThrow(ClientNotFoundException::new);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @GetMapping("/lastName/{lastName}")
    public ResponseEntity<Client> getClientByLastName(@PathVariable String lastName) {
        Client client = clientService.findByLastName(lastName).orElseThrow(ClientNotFoundException::new);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createClient(@RequestBody Client client) {
        clientService.createClient(client);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<HttpStatus> updateClient(@RequestBody Client client) {
        clientService.updateClient(client);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteClient(@PathVariable Integer id) {
        clientService.deleteClientById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
