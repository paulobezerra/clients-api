package io.platformbuilders.clients.infra.services;

import io.platformbuilders.clients.domains.Person;
import io.platformbuilders.clients.infra.enums.PersonCategoryEnum;
import io.platformbuilders.clients.infra.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class ClientService {
    @Autowired
    PersonRepository personRepository;

    public Person save(Person person)
    {
        person.setCategory(PersonCategoryEnum.CLIENT);
        return personRepository.save(person);
    }

    public Page<Person> find(String name, String city, Pageable page) {
        return personRepository.findByNameByDocumentAndByCity(name, city, page);
    }
}
