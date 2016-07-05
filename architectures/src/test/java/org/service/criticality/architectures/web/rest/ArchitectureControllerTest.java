package org.service.criticality.architectures.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.service.criticality.architectures.service.ArchitectureManagement;
import org.service.criticality.architectures.web.rest.domain.ArchitectureDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.stub;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.service.criticality.architectures.util.TestData.sampleArchitectureDTO;

public class ArchitectureControllerTest {

    private ArchitectureController architectureController;

    @Mock
    private ArchitectureManagement architectureManagement;

    @Before
    public void setUp() {
        initMocks(this);

        architectureController = new ArchitectureController(architectureManagement);
    }

    @Test
    public void getAllArchitecturesTest() throws Exception {
        architectureController.getArchitectures(null);

        verify(architectureManagement).getAllArchitectures();
    }

    @Test
    public void getArchitecturesByNameTest() throws Exception {
        architectureController.getArchitectures("arch");

        verify(architectureManagement).getArchitecturesByName("arch");
    }

    @Test
    public void getArchitectureTest() throws Exception {
        architectureController.getArchitecture(1L);

        verify(architectureManagement).getArchitectureById(1L);
    }

    @Test
    public void postArchitectureTest() throws Exception {
        stub(architectureManagement.createNewArchitecture(any(ArchitectureDTO.class))).toReturn(sampleArchitectureDTO());
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final ResponseEntity response = architectureController.postArchitecture(sampleArchitectureDTO());

        verify(architectureManagement).createNewArchitecture(sampleArchitectureDTO());
        assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
    }

    @Test
    public void deleteArchitectureTest() throws Exception {
        architectureController.deleteArchitecture(1L);

        verify(architectureManagement).deleteArchitecture(1L);
    }
}
