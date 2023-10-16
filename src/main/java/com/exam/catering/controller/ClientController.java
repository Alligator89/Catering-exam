package com.exam.catering.controller;

import com.exam.catering.domain.Client;
import com.exam.catering.exceptions.ClientNotFoundException;
import com.exam.catering.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
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

    @Operation(summary = "Get clients")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clients are found"),
            @ApiResponse(responseCode = "404", description = "Clients are not found"),
            @ApiResponse(responseCode = "403", description = "No rights to the content"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),})
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

    @Operation(summary = "Get one client", description = "Get one client , need to pass the input parameter client`s id ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client is found"),
            @ApiResponse(responseCode = "404", description = "Client is not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),})
    @GetMapping("/{id}")
    public ResponseEntity<Client> getOneClient(@PathVariable @Parameter(description = "it is client`s id") Integer id) {
        Client client = clientService.getOneClient(id).orElseThrow(ClientNotFoundException::new);
        log.info("Client with id: " + id + " is found!");
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @Operation(summary = "Get client by lastname", description = "Get client by lastname , need to pass the input parameter client`s lastName ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client by lastname is found"),
            @ApiResponse(responseCode = "404", description = "Client by lastname is not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),})
    @GetMapping("/lastName/{lastName}")
    public ResponseEntity<Client> getClientByLastName(@PathVariable String lastName) {
        Client client = clientService.findByLastName(lastName).orElseThrow(ClientNotFoundException::new);
        log.info("Client with lastname " + lastName + " is found!");
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @Operation(summary = "Creating client", description = "Create client, need to pass the input parameter object Client in format JSON")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Client is created"),
            @ApiResponse(responseCode = "409", description = "Client is not created"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),})
    @PostMapping
    public ResponseEntity<HttpStatus> createClient(@Valid @RequestBody Client client) {
        clientService.createClient(client);
        log.info("Client with firstname " + client.getFirstName() + " is created!");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Updating client", description = "Update client, need to pass the input parameter object Client in format JSON")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Client is updated"),
            @ApiResponse(responseCode = "409", description = "Client is not updated"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),})
    @PutMapping
    public ResponseEntity<HttpStatus> updateClient(@Valid @RequestBody Client client) {
        clientService.updateClient(client);
        log.info("Client with id: " + client.getId() + " is updated!");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Deleting orderedMenu", description = "Delete client,  need to pass the input parameter client`s id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Client is deleted"),
            @ApiResponse(responseCode = "409", description = "Client is not deleted"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),})
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteClient(@PathVariable Integer id) {
        clientService.deleteClientById(id);
        log.info("Client with id: " + id + " is deleted!");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
