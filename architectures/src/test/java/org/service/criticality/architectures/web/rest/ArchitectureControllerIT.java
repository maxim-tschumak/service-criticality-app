package org.service.criticality.architectures.web.rest;

import org.service.criticality.architectures.ArchitectureStacksApp;
import org.service.criticality.architectures.persistence.domain.Architecture;
import org.service.criticality.architectures.persistence.repository.ArchitectureRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.service.criticality.architectures.persistence.repository.ServiceRepository;
import org.service.criticality.architectures.util.DataSourceTestConfiguration;
import org.service.criticality.architectures.web.rest.domain.ArchitectureDTO;
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
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.service.criticality.architectures.util.RegexMatcher.matches;
import static org.service.criticality.architectures.util.TestData.architectureDTOWith3Services;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {DataSourceTestConfiguration.class, ArchitectureStacksApp.class})
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
public class ArchitectureControllerIT {

    @Value("${local.server.port}")
    private int port;

    @Autowired
    private ArchitectureRepository architectureRepository;
    @Autowired
    private ServiceRepository serviceRepository;

    private URL base;
    private RestTemplate template;
    private Architecture storedArchitecture;

    @Before
    public void setUp() throws Exception {
        base = new URL("http://localhost:" + port + "/architectures");
        template = new TestRestTemplate();
        architectureRepository.deleteAll();
        storedArchitecture = architectureRepository
                .save(new Architecture("architecture-stack-1", "some awesome architecture stack"));
    }

    @Test
    public void getArchitecturesTest() throws Exception {
        final ResponseEntity<List> response = template.getForEntity(base.toString(), List.class);

        assertThat(response.getBody().size(), equalTo(1));
    }

    @Test
    public void getArchitectureTest() throws Exception {
        final ResponseEntity<Architecture> response = template
                .getForEntity(base.toString() + "/" + storedArchitecture.getId(), Architecture.class);

        assertThat(response.getBody().getName(), equalTo(storedArchitecture.getName()));
    }

    @Test
    public void postEmptyArchitectureTest() throws Exception {
        final URI location = template.postForLocation(base.toString(),
                ArchitectureDTO.builder().name("super-architecture-stack").description("best distributed system").build());

        assertThat(location.getPath(), matches("^/architectures/[0-9]*$"));
    }

    @Test
    public void postArchitectureWithNestedServicesTest() throws Exception {
        template.postForLocation(base.toString(), architectureDTOWith3Services());

        assertThat(architectureRepository.findByName(architectureDTOWith3Services().getName()), notNullValue());
        assertThat(serviceRepository.findAll().size(), equalTo(3));
    }

}
