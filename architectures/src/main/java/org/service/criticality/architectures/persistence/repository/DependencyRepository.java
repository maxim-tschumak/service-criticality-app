package org.service.criticality.architectures.persistence.repository;

import org.service.criticality.architectures.persistence.domain.Dependency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DependencyRepository extends JpaRepository<Dependency, Long> {
    List<Dependency> findByArchitectureId(Long architectureId);
}
