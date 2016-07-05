package org.service.criticality.architectures.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.service.criticality.architectures.persistence.domain.Dependency;
import org.service.criticality.architectures.persistence.repository.ArchitectureRepository;
import org.service.criticality.architectures.persistence.repository.DependencyRepository;
import org.service.criticality.architectures.persistence.repository.ServiceRepository;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.stub;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.service.criticality.architectures.util.TestData.sampleArchitecture;
import static org.service.criticality.architectures.util.TestData.sampleService;
import static org.service.criticality.architectures.util.TestData.sampleServiceWithOneDependency;

public class DependencyManagementTest {

    private DependencyManagement dependencyManagement;

    @Mock
    private ArchitectureRepository architectureRepository;
    @Mock
    private ServiceRepository serviceRepository;
    @Mock
    private DependencyRepository dependencyRepository;

    @Before
    public void setUp() {
        initMocks(this);

        stub(architectureRepository.findOne(any(Long.class))).toReturn(sampleArchitecture());
        stub(serviceRepository.findOne(any(Long.class))).toReturn(sampleServiceWithOneDependency());

        dependencyManagement = new DependencyManagement(architectureRepository, serviceRepository, dependencyRepository);
    }

    @Test
    public void createDependencyWithinArchitectureTest() throws Exception {
        stub(dependencyRepository.save(any(Dependency.class))).toReturn(new Dependency());

        dependencyManagement.createDependencyWithinArchitecture(1L, 2L, 3L);

        verify(dependencyRepository).save(any(Dependency.class));
    }

    @Test()
    public void deleteDependencyTest() throws Exception {
        dependencyManagement.deleteDependency(1L, 1L, 1L);

        verify(dependencyRepository).delete(any(Long.class));
    }

    @Test
    public void getDependenciesOfAServiceTest() throws Exception {
        stub(serviceRepository.findOne(1L)).toReturn(sampleService());

        final List<Long> dependenciesList = dependencyManagement.getDependenciesOfAService(1L);

        assertThat(dependenciesList.size(), equalTo(0));
    }
}
