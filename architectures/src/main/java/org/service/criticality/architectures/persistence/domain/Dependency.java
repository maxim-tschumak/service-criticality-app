package org.service.criticality.architectures.persistence.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"architecture"})
@ToString(exclude = {"architecture", "service", "dependsOnService"})
@Entity
@Table(schema = "architecture_stacks_data")
public class Dependency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Service service;

    @ManyToOne
    private Service dependsOnService;

    @ManyToOne
    private Architecture architecture;

    public Dependency(final Architecture architecture, final Service service, final Service dependsOnService) {
        this.architecture = architecture;
        this.service = service;
        this.dependsOnService = dependsOnService;
    }
}
