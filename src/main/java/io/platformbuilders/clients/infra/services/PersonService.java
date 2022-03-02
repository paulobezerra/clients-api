package io.platformbuilders.clients.infra.services;

import io.platformbuilders.clients.domains.Person;
import io.platformbuilders.clients.infra.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonService {
    @Autowired
    PersonRepository personRepository;

    public boolean createPerson(Person person) {
        return false;
    }
}
