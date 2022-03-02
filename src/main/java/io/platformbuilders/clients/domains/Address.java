package io.platformbuilders.clients.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name="addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    @Id
    @SequenceGenerator(name="addresses_id_seq", sequenceName="addresses_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="addresses_id_seq")
    @Column(name = "id", updatable=false)
    private Integer id;

    @Column(nullable = false)
    private String publicPlace;

    @Column(nullable = false)
    private String propertyNumber;

    @Column(length = 8)
    private String zipCode;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location city;
}
