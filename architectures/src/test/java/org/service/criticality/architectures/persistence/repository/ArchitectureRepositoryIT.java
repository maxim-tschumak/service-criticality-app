package org.service.criticality.architectures.persistence.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.service.criticality.architectures.ArchitectureStacksApp;
import org.service.criticality.architectures.persistence.domain.Architecture;
import org.service.criticality.architectures.util.DataSourceTestConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.service.criticality.architectures.util.TestData.architectureWith3Services;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {DataSourceTestConfiguration.class, ArchitectureStacksApp.class})
public class ArchitectureRepositoryIT {

    @Autowired
    private ArchitectureRepository architectureRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Before
    public void setUp() {
        serviceRepository.deleteAll();
        architectureRepository.deleteAll();
    }

    @Test
    public void createAndFindByNameTest() throws Exception {
        architectureRepository.save(new Architecture("best-architecture-ever", "awesome architecture stack"));
        final Optional<Architecture> storedArchitecture = architectureRepository.findByName("best-architecture-ever");

        assertTrue(storedArchitecture.isPresent());
        assertEquals(storedArchitecture.get().getName(), "best-architecture-ever");
    }

    @Test
    public void createRichArchitectureTest() throws Exception {
        architectureRepository.save(architectureWith3Services());

        assertThat(serviceRepository.findAll().size(), equalTo(3));
    }

    @Test
    public void findByNameContainingTest() throws Exception {
        architectureRepository.save(new Architecture("architecture-name", "architecture description"));

        assertThat(architectureRepository.findByNameContaining("architect").size(), equalTo(1));
    }
}
