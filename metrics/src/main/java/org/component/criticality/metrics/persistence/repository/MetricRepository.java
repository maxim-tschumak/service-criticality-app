package org.component.criticality.metrics.persistence.repository;

import org.component.criticality.metrics.persistence.domain.Metric;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MetricRepository extends JpaRepository<Metric, Long> {
    List<Metric> findByServiceId(Long serviceId);
    List<Metric> findByArchitectureIdAndNormalizedValueNotNull(Long architectureId);
    Optional<Metric> findByArchitectureIdAndServiceIdAndName(Long architectureId, Long serviceId, String name);
}
