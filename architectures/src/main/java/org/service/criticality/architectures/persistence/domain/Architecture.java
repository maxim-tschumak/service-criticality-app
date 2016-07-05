package org.service.criticality.architectures.persistence.domain;

import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"services", "dependencies"})
@Entity
@Table(schema = "architecture_stacks_data")
public class Architecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "architecture", fetch = FetchType.EAGER)
    private final List<Service> services = new ArrayList<>();

    @OneToMany(mappedBy = "architecture", fetch = FetchType.EAGER)
    private final List<Dependency> dependencies = new ArrayList<>();

    public Architecture(final String name, final String description) {
        this.name = name;
        this.description = description;
    }
}
