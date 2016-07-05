package org.service.criticality.architectures.persistence.repository;

import org.service.criticality.architectures.persistence.domain.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Service, Long> {
    List<Service> findByArchitectureId(Long architectureId);
}
