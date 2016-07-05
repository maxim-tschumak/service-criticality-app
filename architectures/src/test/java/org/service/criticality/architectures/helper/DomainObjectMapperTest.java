package org.service.criticality.architectures.helper;

import org.junit.Test;
import org.service.criticality.architectures.persistence.domain.Architecture;
import org.service.criticality.architectures.web.rest.domain.ArchitectureDTO;
import org.service.criticality.architectures.web.rest.domain.ServiceDTO;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.service.criticality.architectures.helper.DomainObjectMapper.fromArchitecture;
import static org.service.criticality.architectures.helper.DomainObjectMapper.fromService;
import static org.service.criticality.architectures.util.TestData.sampleArchitecture;
import static org.service.criticality.architectures.util.TestData.sampleService;

public class DomainObjectMapperTest {

    @Test
    public void serviceDTOFromServiceTest() throws Exception {
        final ServiceDTO serviceDTO = fromService(sampleService());

        assertThat(serviceDTO.getId(), equalTo(1L));
        assertThat(serviceDTO.getName(), equalTo("Service A"));
        assertThat(serviceDTO.getDescription(), equalTo("#1 service"));
        assertThat(serviceDTO.getArchitectureId(), equalTo(1L));
    }

    @Test
    public void architectureDTOFromArchitectureTest() throws Exception {
        final Architecture architecture = sampleArchitecture();
        architecture.getServices().add(sampleService());

        final ArchitectureDTO architectureDTO = fromArchitecture(architecture);

        assertThat(architectureDTO.getName(), equalTo("Awesome Architecture"));
        assertThat(architectureDTO.getDescription(), equalTo("Microservices Architecture Stack"));
        assertThat(architectureDTO.getId(), equalTo(1L));
        assertThat(architectureDTO.getServices().size(), equalTo(1));
    }
}
