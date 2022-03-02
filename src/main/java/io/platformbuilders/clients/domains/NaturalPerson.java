package io.platformbuilders.clients.domains;

import io.platformbuilders.clients.infra.enums.GenderEnum;
import io.platformbuilders.clients.infra.enums.MaritalStatusEnum;
import io.platformbuilders.clients.infra.enums.PersonTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

@Entity
@Table(name = "natural_people")
@Data
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "person_id")
@EqualsAndHashCode(callSuper=false)
public class NaturalPerson extends Person {
    @Column(nullable = false)
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    @Enumerated(EnumType.STRING)
    private MaritalStatusEnum maritalStatus;
    private String spouseName;
    private String motherName;
    private String fatherName;
    private String rg;
    private String cpf;

    public static NaturalPerson create() {
        NaturalPerson naturalPerson = new NaturalPerson();
        naturalPerson.setType(PersonTypeEnum.NATURAL);
        return naturalPerson;
    }

    @Override
    public String getDocument() {
        return this.cpf;
    }

    @Override
    public Integer getAge() {
        LocalDate current = LocalDate.now();
        return Period.between(this.birthDate, current).getDays();
    }
}
