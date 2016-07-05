package org.service.criticality.architectures.service;

import lombok.extern.slf4j.Slf4j;
import org.service.criticality.architectures.helper.DomainObjectMapper;
import org.service.criticality.architectures.persistence.domain.Architecture;
import org.service.criticality.architectures.persistence.domain.Service;
import org.service.criticality.architectures.persistence.repository.ArchitectureRepository;
import org.service.criticality.architectures.web.rest.domain.ArchitectureDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

import static org.service.criticality.architectures.helper.DomainObjectMapper.fromArchitecture;

@Slf4j
@org.springframework.stereotype.Service
public class ArchitectureManagement {

    private ArchitectureRepository architectureRepository;

    @Autowired
    public ArchitectureManagement(final ArchitectureRepository architectureRepository) {
        this.architectureRepository = architectureRepository;
    }

    public ArchitectureDTO createNewArchitecture(final ArchitectureDTO architectureDTO) {
        final Architecture architecture = architectureRepository.save(constructArchitecture(architectureDTO));
        log.info("New architecture stack has been created: {}", architecture);
        return fromArchitecture(architecture);
    }

    public ArchitectureDTO getArchitectureById(final Long id) {
        return fromArchitecture(architectureRepository.findOne(id));
    }

    public List<ArchitectureDTO> getAllArchitectures() {
        return architectureRepository.findAll().stream()
                .map(DomainObjectMapper::fromArchitecture)
                .collect(Collectors.toList());
    }

    public void deleteArchitecture(final Long id) {
        architectureRepository.delete(id);
        log.info("Architecture stack has been deleted {}", id);
    }

    public List<ArchitectureDTO> getArchitecturesByName(final String name) {
        return architectureRepository.findByNameContaining(name).stream()
                .map(DomainObjectMapper::fromArchitecture)
                .collect(Collectors.toList());
    }

    private Architecture constructArchitecture(final ArchitectureDTO architectureDTO) {
        final Architecture architecture = new Architecture();
        architecture.setName(architectureDTO.getName());
        architecture.setDescription(architectureDTO.getDescription());
        if (architectureDTO.getServices() != null) {
            architectureDTO.getServices().stream().forEach(s -> {
                final Service service = new Service(s.getName(), s.getDescription());
                service.setArchitecture(architecture);
                architecture.getServices().add(service);
            });
        }
        return architecture;
    }
}
