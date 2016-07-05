package org.service.criticality.architectures.helper;

import org.service.criticality.architectures.persistence.domain.Architecture;
import org.service.criticality.architectures.persistence.domain.Service;
import org.service.criticality.architectures.web.rest.domain.ArchitectureDTO;
import org.service.criticality.architectures.web.rest.domain.ServiceDTO;

public class DomainObjectMapper {
    public static ArchitectureDTO fromArchitecture(final Architecture architecture) {
        final ArchitectureDTO architectureDTO = new ArchitectureDTO();
        architectureDTO.setId(architecture.getId());
        architectureDTO.setName(architecture.getName());
        architectureDTO.setDescription(architecture.getDescription());
        architecture.getServices().stream()
                .forEach(service -> architectureDTO.getServices().add(fromService(service)));

        return architectureDTO;
    }

    public static ServiceDTO fromService(final Service service) {
        final ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setId(service.getId());
        serviceDTO.setArchitectureId(service.getArchitecture().getId());
        serviceDTO.setName(service.getName());
        serviceDTO.setDescription(service.getDescription());
        service.getDependencies().stream()
                .forEach(dependency -> serviceDTO.getDependencies().add(dependency.getDependsOnService().getId()));

        return serviceDTO;
    }
}
