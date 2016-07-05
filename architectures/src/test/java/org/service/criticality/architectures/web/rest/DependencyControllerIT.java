package org.service.criticality.architectures.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.service.criticality.architectures.ArchitectureStacksApp;
import org.service.criticality.architectures.persistence.domain.Architecture;
import org.service.criticality.architectures.persistence.domain.Dependency;
import org.service.criticality.architectures.persistence.domain.Service;
import org.service.criticality.architectures.persistence.repository.ArchitectureRepository;
import org.service.criticality.architectures.persistence.repository.DependencyRepository;
import org.service.criticality.architectures.persistence.repository.ServiceRepository;
import org.service.criticality.architectures.util.DataSourceTestConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URL;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.service.criticality.architectures.util.RegexMatcher.matches;
import static org.service.criticality.architectures.util.TestData.architectureWith3Services;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {DataSourceTestConfiguration.class, ArchitectureStacksApp.class})
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
public class DependencyControllerIT {

    @Value("${local.server.port}")
    private int port;

    @Autowired
    private ArchitectureRepository architectureRepository;
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private DependencyRepository dependencyRepository;

    private URL base;
    private RestTemplate template;

    private Architecture storedArchitecture;
    private Service serviceA;
    private Service serviceB;
    private Service serviceC;

    @Before
    public void setUp() throws Exception {
        dependencyRepository.deleteAll();
        serviceRepository.deleteAll();
        architectureRepository.deleteAll();

        storedArchitecture = architectureRepository.save(architectureWith3Services());
        serviceA = storedArchitecture.getServices().get(0);
        serviceB = storedArchitecture.getServices().get(1);
        serviceC = storedArchitecture.getServices().get(2);
        dependencyRepository.save(new Dependency(storedArchitecture, serviceA, serviceB));

        template = new TestRestTemplate();
        base = new URL("http://localhost:" + port + "/architectures/" + storedArchitecture.getId() + "/services/");
    }

    @Test
    public void getDependencyTest() throws Exception {
        final Dependency dependency = new Dependency(storedArchitecture, serviceB, serviceA);
        dependencyRepository.save(dependency);

        final ResponseEntity<List> response = template.getForEntity(base.toString() + serviceA.getId() + "/dependencies", List.class);

        assertThat(response.getBody().size(), equalTo(1));
    }

    @Test
    public void addDependencyTest() throws Exception {
        final URI location = template.postForLocation(base.toString() + serviceA.getId() + "/dependencies/" + serviceC.getId(), null);

        assertThat(location.getPath(), matches("^/architectures/[0-9]*/services/[0-9]*/dependencies/[0-9]*$"));
    }
}
