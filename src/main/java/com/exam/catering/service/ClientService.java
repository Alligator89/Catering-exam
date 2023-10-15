package com.exam.catering.service;

import com.exam.catering.domain.Client;
import com.exam.catering.exceptions.ClientNotFoundException;
import com.exam.catering.repository.ClientRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    private final SecurityCredentialsRepository securityCredentialsRepository;

    public ClientService(ClientRepository clientRepository, SecurityCredentialsRepository securityCredentialsRepository) {
        this.clientRepository = clientRepository;
        this.securityCredentialsRepository = securityCredentialsRepository;
    }

    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    public Optional<Client> getOneClient(Integer id) {
        Optional<Client> client = clientRepository.findById(id);
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<SecurityCredentials> clientLogin = securityCredentialsRepository.findByClientLogin(login);
        if (clientLogin.isPresent()) {
            Integer clientId = clientLogin.get().getClientId();
            if (clientId.equals(id)) {
                return client;
            }
            return Optional.empty();
        }
        throw new ClientNotFoundException();
    }

    public Optional<Client> findByLastName(String lastName) {
        return clientRepository.findClientByLastName(lastName);
    }

    public void createClient(Client client) {
        client.setFirstName(client.getFirstName());
        client.setLastName(client.getLastName());
        client.setEmailAddress(client.getEmailAddress());
        client.setPhoneNumber(client.getPhoneNumber());
        clientRepository.save(client);
    }

    public void updateClient(Client client) {
        client.setId(client.getId());
        client.setFirstName(client.getFirstName());
        client.setLastName(client.getLastName());
        client.setEmailAddress(client.getEmailAddress());
        client.setPhoneNumber(client.getPhoneNumber());
        clientRepository.saveAndFlush(client);
    }

    public void deleteClientById(Integer id) {
        clientRepository.deleteById(id);
    }
}
