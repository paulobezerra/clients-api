package io.platformbuilders.clients.domains;

import io.platformbuilders.clients.infra.enums.LocationTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name="locations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location {
    @Id
    @SequenceGenerator(name="locations_id_seq", sequenceName="locations_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="locations_id_seq")
    @Column(name = "id", updatable=false)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private LocationTypeEnum type;

    @ManyToOne
    @JoinColumn(name = "parent_location_id")
    private Location parent;
}
