package org.service.criticality.architectures.util;

import org.service.criticality.architectures.persistence.domain.Architecture;
import org.service.criticality.architectures.persistence.domain.Dependency;
import org.service.criticality.architectures.persistence.domain.Service;
import org.service.criticality.architectures.web.rest.domain.ArchitectureDTO;
import org.service.criticality.architectures.web.rest.domain.ServiceDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestData {

    public static ArchitectureDTO architectureDTOWith3Services() {
        final ArchitectureDTO architectureDTO = ArchitectureDTO.builder().name("big-arch")
                .description("Super big architecture stack").services(new ArrayList<>()).build();

        final ServiceDTO serviceA = ServiceDTO.builder().name("service-a").description("Service A").dependencies(new ArrayList<>()).build();
        final ServiceDTO serviceB = ServiceDTO.builder().name("service-b").description("Service B").dependencies(new ArrayList<>()).build();
        final ServiceDTO serviceC = ServiceDTO.builder().name("service-c").description("Service C").dependencies(new ArrayList<>()).build();
        final List<ServiceDTO> serviceDTOs = new ArrayList<>(Arrays.asList(serviceA, serviceB, serviceC));

        architectureDTO.getServices().addAll(serviceDTOs);
        return architectureDTO;
    }

    public static Architecture architectureWith3Services() {
        final Architecture architecture = new Architecture("mega-arch", "Super awesome architecture stack");

        final Service serviceA = new Service(architecture, "service-a", "Service A");
        final Service serviceB = new Service(architecture, "service-b", "Service B");
        final Service serviceC = new Service(architecture, "service-c", "Service C");
        final List<Service> services = new ArrayList<>(Arrays.asList(serviceA, serviceB, serviceC));

        architecture.getServices().addAll(services);
        return architecture;
    }

    public static Architecture sampleArchitecture() {
        return Architecture.builder()
                .id(1L)
                .name("Awesome Architecture")
                .description("Microservices Architecture Stack")
                .build();
    }

    public static Service sampleService() {
        return Service.builder()
                .id(1L)
                .name("Service A")
                .architecture(sampleArchitecture())
                .description("#1 service")
                .build();
    }

    public static ServiceDTO sampleServiceDTO() {
        return ServiceDTO.builder().id(1L).name("Service A").build();
    }

    public static Service sampleServiceWithOneDependency() {
        final Service serviceA = Service.builder().id(1L).name("Service A").architecture(sampleArchitecture()).description("service #1").build();
        final Service serviceB = Service.builder().id(1L).name("Service B").architecture(sampleArchitecture()).description("service #2").build();

        final Dependency dependency = new Dependency(sampleArchitecture(), serviceA, serviceB);
        serviceA.getDependencies().add(dependency);

        return serviceA;
    }

    public static ArchitectureDTO sampleArchitectureDTO() {
        return ArchitectureDTO.builder()
                .id(1L)
                .name("Another Architecture Stack")
                .description("super scalable architecture")
                .build();
    }
}
