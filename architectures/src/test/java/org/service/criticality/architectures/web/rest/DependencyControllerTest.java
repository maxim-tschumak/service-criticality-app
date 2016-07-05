package org.service.criticality.architectures.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.service.criticality.architectures.service.DependencyManagement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class DependencyControllerTest {

    private DependencyController dependencyController;

    @Mock
    private DependencyManagement dependencyManagement;

    @Before
    public void setUp() {
        initMocks(this);

        dependencyController = new DependencyController(dependencyManagement);
    }

    @Test
    public void getDependenciesByServiceId() throws Exception {
        dependencyController.getDependencies(1L);

        verify(dependencyManagement).getDependenciesOfAService(1L);
    }

    @Test
    public void addNewDependencyToArchitectureTest() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final ResponseEntity response = dependencyController.addNewDependencyToArchitecture(1L, 2L, 3L);

        verify(dependencyManagement).createDependencyWithinArchitecture(1L, 2L, 3L);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
    }

    @Test
    public void deleteDependencyTest() throws Exception {
        dependencyController.deleteDependency(1L, 2L, 3L);

        verify(dependencyManagement).deleteDependency(1L, 2L, 3L);
    }
}
