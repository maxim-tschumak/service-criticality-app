package org.service.criticality.architectures.service;

import lombok.extern.slf4j.Slf4j;
import org.service.criticality.architectures.persistence.domain.Architecture;
import org.service.criticality.architectures.persistence.domain.Dependency;
import org.service.criticality.architectures.persistence.domain.Service;
import org.service.criticality.architectures.persistence.repository.ArchitectureRepository;
import org.service.criticality.architectures.persistence.repository.DependencyRepository;
import org.service.criticality.architectures.persistence.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@org.springframework.stereotype.Service
public class DependencyManagement {

    private ArchitectureRepository architectureRepository;

    private ServiceRepository serviceRepository;

    private DependencyRepository dependencyRepository;

    @Autowired
    public DependencyManagement(final ArchitectureRepository architectureRepository, final ServiceRepository serviceRepository, final DependencyRepository dependencyRepository) {
        this.architectureRepository = architectureRepository;
        this.serviceRepository = serviceRepository;
        this.dependencyRepository = dependencyRepository;
    }

    public Long createDependencyWithinArchitecture(final Long architectureId, final Long serviceId, final Long dependsOnServiceId) {
        final Architecture architecture = architectureRepository.findOne(architectureId);
        final Service service = serviceRepository.findOne(serviceId);
        final Service dependsOnService = serviceRepository.findOne(dependsOnServiceId);

        final Dependency dependency = new Dependency();
        dependency.setArchitecture(architecture);
        dependency.setService(service);
        dependency.setDependsOnService(dependsOnService);

        final Dependency savedDependency = dependencyRepository.save(dependency);

        log.info("New dependency in architecture {} has been created: {}", architectureId, savedDependency);
        return savedDependency.getId();
    }

    public void deleteDependency(final Long architectureId, final Long serviceId, final Long dependsOnServiceId) {
        final Service service = serviceRepository.findOne(serviceId);
        final Optional<Dependency> dependencyOptional = service.getDependencies().stream()
                .filter(d -> d.getDependsOnService().getId() == dependsOnServiceId.longValue()).findFirst();

        if (dependencyOptional.isPresent()) {
            final Dependency dependency = dependencyOptional.get();
            if (architectureId == dependency.getArchitecture().getId().longValue() ||
                    serviceId == dependency.getService().getId().longValue()) {
                dependencyRepository.delete(dependency.getId());
                dependencyRepository.flush();
                log.info("Dependency {} has been deleted from architecture {}", dependency.getId(), architectureId);
            } else {
                log.warn("Someone tried to delete a service which doesn't below to current architecture/service: " +
                        "architecture {}, service {}, dependsOnService {}", architectureId, serviceId, dependsOnServiceId);
            }
        } else {
            log.warn("There is no such dependency: architecture {}, service {}, dependsOnService {}", architectureId,
                    serviceId, dependsOnServiceId);
        }
    }

    public List<Long> getDependenciesOfAService(final Long serviceId) {
        final Service service = serviceRepository.findOne(serviceId);
        return service.getDependencies().stream().map(Dependency::getId).collect(Collectors.toList());
    }
}
