package io.platformbuilders.clients.infra.repositories;

import io.platformbuilders.clients.domains.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository  extends CrudRepository<Person, Integer> {
    Person findByName(String name);
}
