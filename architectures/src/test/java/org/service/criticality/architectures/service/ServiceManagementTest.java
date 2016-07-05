package org.service.criticality.architectures.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.service.criticality.architectures.persistence.domain.Service;
import org.service.criticality.architectures.persistence.repository.ArchitectureRepository;
import org.service.criticality.architectures.persistence.repository.ServiceRepository;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.stub;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.service.criticality.architectures.util.TestData.sampleService;
import static org.service.criticality.architectures.util.TestData.sampleServiceDTO;

public class ServiceManagementTest {

    private ServiceManagement serviceManagement;

    @Mock
    private ArchitectureRepository architectureRepository;

    @Mock
    private ServiceRepository serviceRepository;

    @Before
    public void setUp() {
        initMocks(this);

        serviceManagement = new ServiceManagement(architectureRepository, serviceRepository);
    }

    @Test
    public void getServicesByArchitectureIdTest() throws Exception {
        serviceManagement.getServicesByArchitectureId(1L);

        verify(serviceRepository).findByArchitectureId(1L);
    }

    @Test
    public void createServiceWithinArchitectureTest() throws Exception {
        stub(serviceRepository.save(any(Service.class))).toReturn(sampleService());

        serviceManagement.createServiceWithinArchitecture(sampleServiceDTO(), 1L);

        verify(serviceRepository).save(any(Service.class));
    }

    @Test
    public void deleteServiceTest() throws Exception {
        stub(serviceRepository.findOne(1L)).toReturn(sampleService());

        serviceManagement.deleteService(1L, 1L);

        verify(serviceRepository).delete(1L);
    }
}
