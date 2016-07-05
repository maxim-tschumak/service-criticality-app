package org.service.criticality.architectures.service;

import lombok.extern.slf4j.Slf4j;
import org.service.criticality.architectures.helper.DomainObjectMapper;
import org.service.criticality.architectures.persistence.domain.Architecture;
import org.service.criticality.architectures.persistence.domain.Service;
import org.service.criticality.architectures.persistence.repository.ArchitectureRepository;
import org.service.criticality.architectures.persistence.repository.ServiceRepository;
import org.service.criticality.architectures.web.rest.domain.ServiceDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

import static org.service.criticality.architectures.helper.DomainObjectMapper.fromService;

@Slf4j
@org.springframework.stereotype.Service
public class ServiceManagement {

    private ServiceRepository serviceRepository;

    private ArchitectureRepository architectureRepository;

    @Autowired
    public ServiceManagement(final ArchitectureRepository architectureRepository, final ServiceRepository serviceRepository) {
        this.architectureRepository = architectureRepository;
        this.serviceRepository = serviceRepository;
    }

    public List<ServiceDTO> getServicesByArchitectureId(final Long architectureId) {
        return serviceRepository.findByArchitectureId(architectureId).stream()
                .map(DomainObjectMapper::fromService)
                .collect(Collectors.toList());
    }

    public ServiceDTO createServiceWithinArchitecture(final ServiceDTO serviceDTO, final Long architectureId) {
        final Architecture architecture = architectureRepository.findOne(architectureId);
        final Service service = constructService(serviceDTO);
        service.setArchitecture(architecture);

        final Service savedService = serviceRepository.save(service);

        log.info("New service in architecture {} has been created: {}", architectureId, savedService);
        return fromService(savedService);
    }

    public void deleteService(final Long architectureId, final Long serviceId) {
        final Service service = serviceRepository.findOne(serviceId);
        if (architectureId == service.getArchitecture().getId().longValue()) {
            serviceRepository.delete(serviceId);
            log.info("Service {} has been deleted from architecture {}", serviceId, architectureId);
        } else {
            log.warn("Someone tried to delete a service which doesn't below to current architecture stack");
        }
    }

    private static Service constructService(final ServiceDTO serviceDTO) {
        final Service service = new Service();
        service.setName(serviceDTO.getName());
        service.setDescription(serviceDTO.getDescription());

        return service;
    }
}
