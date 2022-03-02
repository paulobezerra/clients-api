package io.platformbuilders.clients.services;

import io.platformbuilders.clients.domains.Address;
import io.platformbuilders.clients.domains.Location;
import io.platformbuilders.clients.domains.NaturalPerson;
import io.platformbuilders.clients.domains.Person;
import io.platformbuilders.clients.infra.enums.GenderEnum;
import io.platformbuilders.clients.infra.enums.LocationTypeEnum;
import io.platformbuilders.clients.infra.enums.MaritalStatusEnum;
import io.platformbuilders.clients.infra.enums.PersonCategoryEnum;
import io.platformbuilders.clients.infra.repositories.PersonRepository;
import io.platformbuilders.clients.infra.services.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @InjectMocks
    ClientService clientService;

    @Mock
    PersonRepository personRepositoryMock;

    private NaturalPerson naturalPerson;
    private Location city;

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
    }

    
    @Test
    void mustCreateClient() throws CloneNotSupportedException {
        NaturalPerson personClone = NaturalPerson.create();
        personClone.setId(1);
        when(this.personRepositoryMock.save(any())).thenReturn(personClone);

        Person newPerson = this.clientService.save(naturalPerson);

        assertEquals(1, newPerson.getId());
        assertNull(naturalPerson.getId());
    }

    @Test
    void mustFindByCity() {
        Page<Person> personPage = new PageImpl<Person>(List.of(naturalPerson), PageRequest.of(0, 10), 10);
        when(this.personRepositoryMock.findByNameByDocumentAndByCity(any(), any(), any())).thenReturn(personPage);

        Page<Person> page = this.clientService.find(null, "Campo Grande", PageRequest.of(0, 10));

        assertEquals(10, page.getSize());
        assertEquals(1, page.getNumberOfElements());
        assertTrue(page.get().findFirst().isPresent());
        assertEquals("Joe Doe", page.get().findFirst().get().getName());
    }


}