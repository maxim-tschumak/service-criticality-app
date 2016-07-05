package org.service.criticality.architectures.persistence.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"architecture"})
@ToString(exclude = {"architecture", "dependencies"})
@Entity
@Table(schema = "architecture_stacks_data")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @OneToMany(mappedBy = "service")
    private final List<Dependency> dependencies = new ArrayList<>();

    @ManyToOne
    private Architecture architecture;

    public Service(final Architecture architecture, final String name, final String description) {
        this.architecture = architecture;
        this.name = name;
        this.description = description;
    }

    public Service(final String name, final String description){
        this.name = name;
        this.description = description;
    }
}
