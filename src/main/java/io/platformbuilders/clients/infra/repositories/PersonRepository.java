package io.platformbuilders.clients.infra.repositories;

import io.platformbuilders.clients.domains.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository  extends CrudRepository<Person, Integer> {

    @Query("SELECT p " +
            "FROM Person p " +
            "JOIN p.address a " +
            "JOIN a.city c "+
            "JOIN p.phones ph "+
            "WHERE (:name is null or lower(p.name) like lower(concat('%', :name, '%'))) " +
            "OR (:city is null or c.name = :city)"
    )
    Page<Person> findByNameByDocumentAndByCity(
            @Param("name") String name,
            @Param("city") String city,
            Pageable pageable
    );
}
