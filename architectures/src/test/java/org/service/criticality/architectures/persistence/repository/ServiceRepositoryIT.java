package org.service.criticality.architectures.persistence.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.service.criticality.architectures.ArchitectureStacksApp;
import org.service.criticality.architectures.persistence.domain.Architecture;
import org.service.criticality.architectures.persistence.domain.Service;
import org.service.criticality.architectures.util.DataSourceTestConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {DataSourceTestConfiguration.class, ArchitectureStacksApp.class})
public class ServiceRepositoryIT {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ArchitectureRepository architectureRepository;

    @Before
    public void setUp() {
        architectureRepository.deleteAll();
        serviceRepository.deleteAll();
    }

    @Test
    public void addServiceToArchitectureTest() throws Exception {
        final Architecture storedArchitecture = architectureRepository.save(new Architecture("arch-1", "number one"));
        serviceRepository.save(new Service(storedArchitecture, "service-a", "Service A"));

        assertThat(serviceRepository.findByArchitectureId(storedArchitecture.getId()).size(), equalTo(1));
    }
}
