package org.service.criticality.architectures.persistence.repository;

import org.service.criticality.architectures.persistence.domain.Architecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArchitectureRepository extends JpaRepository<Architecture, Long> {
    Optional<Architecture> findByName(String name);

    List<Architecture> findByNameContaining(String subString);
}
