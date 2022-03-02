package io.platformbuilders.clients.services;

import io.platformbuilders.clients.infra.repositories.PersonRepository;
import io.platformbuilders.clients.infra.services.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @MockBean
    PersonService personService;

    @Mock
    PersonRepository personRepository;

    @BeforeEach
    void init() {
        personService = new PersonService();
    }



}