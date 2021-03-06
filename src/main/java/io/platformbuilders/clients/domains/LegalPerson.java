package io.platformbuilders.clients.domains;

import io.platformbuilders.clients.infra.enums.PersonTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "legal_people")
@Data
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "person_id")
@EqualsAndHashCode(callSuper=false)
public class LegalPerson extends Person {
    @Column(nullable = false)
    private LocalDate openingDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "contacts",
            joinColumns = @JoinColumn(name = "legal_person_id"),
            inverseJoinColumns = @JoinColumn(name = "natural_person_id"))
    private Set<NaturalPerson> contacts;

    private String ie;

    private String cnpj;

    public void addContact(NaturalPerson person) {
        this.contacts.add(person);
    }

    public static LegalPerson create() {
        LegalPerson legalPerson = new LegalPerson();
        legalPerson.setType(PersonTypeEnum.LEGAL);
        legalPerson.contacts = new HashSet<>();
        return legalPerson;
    }

    @Override
    public String getDocument() {
        return this.cnpj;
    }

    @Override
    public Integer getAge() {
        LocalDate current = LocalDate.now();
        return Period.between(this.openingDate, current).getDays();
    }
}
