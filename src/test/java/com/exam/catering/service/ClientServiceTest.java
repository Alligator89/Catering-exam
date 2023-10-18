package com.exam.catering.service;

import com.exam.catering.domain.Client;
import com.exam.catering.repository.ClientRepository;
import com.exam.catering.security.domain.SecurityCredentials;
import com.exam.catering.security.repository.SecurityCredentialsRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    private static final Integer ID_CLIENT = 4;

    private final static String SURNAME = " Иванов";

    private final static String NAME = "Дима";

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private SecurityCredentialsRepository securityCredentialsRepository;

    @BeforeAll
    public static void beforeAll() {
        Authentication authenticationMock = mock(Authentication.class);
        SecurityContext securityContextMock = mock(SecurityContext.class);
        Mockito.when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);
        SecurityContextHolder.setContext(securityContextMock);
    }

    @Test
    public void getClientsTest() {
        clientService.getClients();
        Mockito.verify(clientRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void getOneClientTest() {
        SecurityContextHolder.setContext(new SecurityContext() {
            @Override
            public Authentication getAuthentication() {
                return new Authentication() {
                    @Override
                    public Collection<? extends GrantedAuthority> getAuthorities() {
                        return null;
                    }

                    @Override
                    public Object getCredentials() {
                        return null;
                    }

                    @Override
                    public Object getDetails() {
                        return null;
                    }

                    @Override
                    public Object getPrincipal() {
                        return null;
                    }

                    @Override
                    public boolean isAuthenticated() {
                        return false;
                    }

                    @Override
                    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
                    }

                    @Override
                    public String getName() {
                        return NAME;
                    }
                };
            }

            @Override
            public void setAuthentication(Authentication authentication) {
            }
        });
        SecurityCredentials securityCredentials = new SecurityCredentials();
        securityCredentials.setClientId(ID_CLIENT);
        Mockito.when(securityCredentialsRepository.findByClientLogin(NAME)).thenReturn(Optional.of(securityCredentials));
        clientService.getOneClient(ID_CLIENT);
        Mockito.verify(clientRepository, Mockito.times(1)).findById(ID_CLIENT);
    }

    @Test
    public void getClientByLastNameTest() {
        clientService.findByLastName(SURNAME);
        Mockito.verify(clientRepository, Mockito.times(1)).findClientByLastName(SURNAME);
    }

    @Test
    public void createClient() {
        clientService.createClient(new Client());
        Mockito.verify(clientRepository, Mockito.times(1)).save(any());
    }

    @Test
    public void updateClient() {
        clientService.updateClient(new Client());
        Mockito.verify(clientRepository, Mockito.times(1)).saveAndFlush(any());
    }

    @Test
    public void deleteClient() {
        clientService.deleteClientById(ID_CLIENT);
        Mockito.verify(clientRepository, Mockito.times(1)).deleteById(anyInt());
    }
}
