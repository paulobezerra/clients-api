package io.platformbuilders.clients.infra.repositories;

import io.platformbuilders.clients.domains.*;
import io.platformbuilders.clients.infra.enums.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@DataJpaTest
@Sql(scripts = "classpath:db/data.sql")
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;
    private Location city;
    private NaturalPerson naturalPerson;
    private LegalPerson legalPerson;

    @BeforeEach
    void init() {
        Location country = Location
                .builder()
                .name("Brazil")
                .type(LocationTypeEnum.COUNTRY)
                .build();
        Location state = Location
                .builder()
                .name("Mato Grosso do Sul")
                .type(LocationTypeEnum.STATE)
                .parent(country)
                .build();
        this.city = Location
                .builder()
                .name("Campo Grande")
                .type(LocationTypeEnum.CITY)
                .parent(state)
                .build();

        Address address = Address
                .builder()
                .publicPlace("St. Anger")
                .propertyNumber("66")
                .city(city)
                .build();

        this.naturalPerson = NaturalPerson.create();
        naturalPerson.setCategory(PersonCategoryEnum.CLIENT);
        naturalPerson.setName("Joe Doe");
        naturalPerson.setAddress(address);
        naturalPerson.setBirthDate(LocalDate.of(1986, 3, 24));
        naturalPerson.setGender(GenderEnum.MALE);
        naturalPerson.setMaritalStatus(MaritalStatusEnum.MARRIED);
        naturalPerson.setSpouseName("Rose Doe");
        naturalPerson.setMotherName("Mary Doe");
        naturalPerson.setFatherName("Bob Doe");
        naturalPerson.setCpf("12345678912");
        naturalPerson.setRg("12354654");

        this.legalPerson = LegalPerson.create();
        legalPerson.setName("ACME Inc.");
        legalPerson.setOpeningDate(LocalDate.of(2019, 12, 26));
        legalPerson.setAddress(address);
        legalPerson.addContact(naturalPerson);
        legalPerson.setCnpj("123456789123456");
        legalPerson.setIe("123456789123456");

        Phone phoneNatualPerson = Phone
                .builder()
                .person(naturalPerson)
                .type("Residential")
                .number("123456")
                .build();
        naturalPerson.addPhone(phoneNatualPerson);

        Phone phoneLegalPerson = Phone
                .builder()
                .person(legalPerson)
                .type("Telemarketing")
                .number("123456")
                .build();
        legalPerson.addPhone(phoneLegalPerson);
    }

    @Test
    void mustSaveNaturalPersonAndRecoverById() {
        Person person = naturalPerson;
        this.personRepository.save(person);

        Optional<Person> personOptional = this.personRepository.findById(person.getId());

        assertTrue(personOptional.isPresent());

        Person personDb = personOptional.get();
        assertEquals(person.getName(), personDb.getName());
        assertEquals(person.getType(), personDb.getType());
        assertEquals(person.getCategory(), personDb.getCategory());
        assertEquals(person.getAddress().getPublicPlace(), personDb.getAddress().getPublicPlace());
        assertEquals(city.getName(), personDb.getAddress().getCity().getName());

        NaturalPerson naturalPersonDb = (NaturalPerson) personDb;
        assertEquals(naturalPerson.getCpf(), naturalPersonDb.getCpf());
    }

    @Test
    void mustSaveLegalPersonAndRecoverById() {
        this.personRepository.save(naturalPerson);
        this.personRepository.save(legalPerson);

        Optional<Person> personOptional = this.personRepository.findById(legalPerson.getId());

        assertTrue(personOptional.isPresent());

        Person personDb = personOptional.get();
        assertEquals(legalPerson.getName(), personDb.getName());
        assertEquals(legalPerson.getType(), personDb.getType());
        assertEquals(legalPerson.getCategory(), personDb.getCategory());
        assertEquals(legalPerson.getAddress().getPublicPlace(), personDb.getAddress().getPublicPlace());
        assertEquals(city.getName(), personDb.getAddress().getCity().getName());

        LegalPerson legalPersonDB = (LegalPerson) personDb;
        assertEquals(legalPersonDB.getCnpj(), legalPerson.getCnpj());
        NaturalPerson contact = (NaturalPerson) legalPersonDB.getContacts().toArray()[0];
        assertEquals(naturalPerson.getId(), contact.getId());
    }

    @Test
    void mustReturnAllPeople() {
        Iterable<Person> peopleIterable = this.personRepository.findAll();
        List<Person> people = StreamSupport.stream(peopleIterable.spliterator(), false).toList();
        assertEquals(2, people.size());
        assertEquals(NaturalPerson.class, people.get(0).getClass());
        assertEquals("Joe Doe", people.get(0).getName());
        assertEquals("St Angel", people.get(0).getAddress().getPublicPlace());
        assertTrue(people.get(0).getPhones().stream().findFirst().isPresent());
        assertEquals("Residential", people.get(0).getPhones().stream().findFirst().get().getType());
        assertEquals(LegalPerson.class, people.get(1).getClass());
        assertEquals("ACME Inc.", people.get(1).getName());
        assertEquals("St Angel", people.get(1).getAddress().getPublicPlace());
        assertTrue(people.get(1).getPhones().stream().findFirst().isPresent());
        assertEquals("Telemarketing", people.get(1).getPhones().stream().findFirst().get().getType());
    }

    @Test
    void mustReturnPeopleByNameAndByCity() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Person> people = this.personRepository.findByNameByDocumentAndByCity(null, "Campo Grande", pageable);
        assertNotNull(people);
        assertEquals(2, people.getTotalElements());
    }

}