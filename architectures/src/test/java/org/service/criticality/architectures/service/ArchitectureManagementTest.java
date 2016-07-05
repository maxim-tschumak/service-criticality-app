package org.service.criticality.architectures.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.service.criticality.architectures.persistence.domain.Architecture;
import org.service.criticality.architectures.persistence.repository.ArchitectureRepository;
import org.service.criticality.architectures.web.rest.domain.ArchitectureDTO;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.stub;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.service.criticality.architectures.util.TestData.sampleArchitecture;
import static org.service.criticality.architectures.util.TestData.sampleArchitectureDTO;

public class ArchitectureManagementTest {

    private ArchitectureManagement architectureManagement;

    @Mock
    private ArchitectureRepository architectureRepository;

    @Before
    public void setUp() {
        initMocks(this);

        architectureManagement = new ArchitectureManagement(architectureRepository);
    }

    @Test
    public void createNewArchitectureTest() throws Exception {
        stub(architectureRepository.save(any(Architecture.class))).toReturn(sampleArchitecture());

        architectureManagement.createNewArchitecture(sampleArchitectureDTO());

        verify(architectureRepository).save(any(Architecture.class));
    }

    @Test
    public void getArchitectureByIdTest() throws Exception {
        stub(architectureRepository.findOne(any(Long.class))).toReturn(sampleArchitecture());

        final ArchitectureDTO architectureDTO = architectureManagement.getArchitectureById(1L);

        verify(architectureRepository).findOne(1L);
        assertNotNull(architectureDTO);
    }

    @Test
    public void getAllArchitecturesTest() throws Exception {
        architectureManagement.getAllArchitectures();

        verify(architectureRepository).findAll();
    }

    @Test
    public void deleteArchitectureTest() throws Exception {
        architectureManagement.deleteArchitecture(1L);

        verify(architectureRepository).delete(1L);
    }

    @Test
    public void getArchitecturesByNameTest() throws Exception {
        architectureManagement.getArchitecturesByName("awesome-arch");

        verify(architectureRepository).findByNameContaining("awesome-arch");
    }
}
