package org.service.criticality.architectures.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.service.criticality.architectures.ArchitectureStacksApp;
import org.service.criticality.architectures.persistence.domain.Architecture;
import org.service.criticality.architectures.persistence.domain.Service;
import org.service.criticality.architectures.persistence.repository.ArchitectureRepository;
import org.service.criticality.architectures.persistence.repository.DependencyRepository;
import org.service.criticality.architectures.persistence.repository.ServiceRepository;
import org.service.criticality.architectures.util.DataSourceTestConfiguration;
import org.service.criticality.architectures.web.rest.domain.ServiceDTO;
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

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {DataSourceTestConfiguration.class, ArchitectureStacksApp.class})
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
public class ServiceControllerIT {

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

    @Before
    public void setUp() throws Exception {
        dependencyRepository.deleteAll();
        serviceRepository.deleteAll();
        architectureRepository.deleteAll();

        storedArchitecture = architectureRepository
                .save(new Architecture("architecture-stack-1", "some awesome architecture stack"));
        serviceRepository.save(new Service(storedArchitecture, "service-1", "first service"));

        template = new TestRestTemplate();
        base = new URL("http://localhost:" + port + "/architectures/" + storedArchitecture.getId() + "/services");
    }

    @Test
    public void getServiceTest() throws Exception {
        final ResponseEntity<List> response = template.getForEntity(base.toString(), List.class);

        assertThat(response.getBody().size(), equalTo(1));
    }

    @Test
    public void postServiceTest() throws Exception {
        final ServiceDTO serviceDTO = ServiceDTO.builder().name("Service A").build();
        final URI location = template.postForLocation(base.toString(), serviceDTO);

        assertThat(location.getPath(), matches("^/architectures/" + storedArchitecture.getId() + "/services/[0-9]*$"));
    }
}
