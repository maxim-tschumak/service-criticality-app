package org.service.criticality.architectures.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.service.criticality.architectures.service.ServiceManagement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.stub;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.service.criticality.architectures.util.TestData.sampleServiceDTO;

public class ServiceControllerTest {

    private ServiceController serviceController;

    @Mock
    private ServiceManagement serviceManagement;

    @Before
    public void setUp() {
        initMocks(this);

        serviceController = new ServiceController(serviceManagement);
    }

    @Test
    public void getAllServicesOfArchitecture() throws Exception {
        serviceController.getAllServicesOfArchitecture(1L);

        verify(serviceManagement).getServicesByArchitectureId(1L);
    }

    @Test
    public void addNewServiceToArchitectureTest() throws Exception {
        stub(serviceManagement.createServiceWithinArchitecture(sampleServiceDTO(), 1L)).toReturn(sampleServiceDTO());
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final ResponseEntity response = serviceController.addNewServiceToArchitecture(1L, sampleServiceDTO());

        verify(serviceManagement).createServiceWithinArchitecture(sampleServiceDTO(), 1L);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
    }

    @Test
    public void deleteServiceTest() throws Exception {
        serviceController.deleteService(1L, 2L);

        verify(serviceManagement).deleteService(1L, 2L);
    }
}
