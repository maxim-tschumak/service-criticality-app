package org.service.criticality.architectures.persistence.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.service.criticality.architectures.ArchitectureStacksApp;
import org.service.criticality.architectures.persistence.domain.Architecture;
import org.service.criticality.architectures.persistence.domain.Dependency;
import org.service.criticality.architectures.persistence.domain.Service;
import org.service.criticality.architectures.util.DataSourceTestConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {DataSourceTestConfiguration.class, ArchitectureStacksApp.class})

public class DependencyRepositoryIT {

    @Autowired
    private DependencyRepository dependencyRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ArchitectureRepository architectureRepository;

    @Before
    public void setUp() {
        dependencyRepository.deleteAll();
        serviceRepository.deleteAll();
        architectureRepository.deleteAll();
    }

    @Test
    public void addServiceToArchitectureTest() throws Exception {
        final Dependency dependency = storeDependency();

        assertThat(dependencyRepository.findByArchitectureId(dependency.getArchitecture().getId()).size(), equalTo(1));
    }

    @Test
    public void deleteExistingDependencyTest() throws Exception {
        final Dependency dependency = storeDependency();

        dependencyRepository.delete(dependency.getId());

        assertThat(dependencyRepository.findAll().size(), equalTo(0));
    }

    private Dependency storeDependency() {
        final Architecture architecture = architectureRepository.save(new Architecture("arch-1", "number one"));
        final Service serviceA = serviceRepository.save(new Service(architecture, "service-a", "Service A"));
        final Service serviceB = serviceRepository.save(new Service(architecture, "service-b", "Service B"));
        return dependencyRepository.save(new Dependency(architecture, serviceA, serviceB));
    }
}
