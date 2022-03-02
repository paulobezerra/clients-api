package io.platformbuilders.clients.domains;

import io.platformbuilders.clients.infra.converters.PersonTypeConverter;
import io.platformbuilders.clients.infra.enums.PersonCategoryEnum;
import io.platformbuilders.clients.infra.enums.PersonTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="people")
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Person {
    @Id
    @SequenceGenerator(name="people_id_seq", sequenceName="people_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="people_id_seq")
    @Column(name = "id", updatable=false)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Convert(converter = PersonTypeConverter.class)
    private PersonTypeEnum type;

    @Enumerated(EnumType.STRING)
    private PersonCategoryEnum category;

    @Column(nullable = false, length = 100)
    private String name;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY)
    private Set<Phone> phones = new HashSet<>();

    public void addPhone(Phone phone) {
        this.phones.add(phone);
    }
}
