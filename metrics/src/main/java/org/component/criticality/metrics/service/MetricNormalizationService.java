package org.component.criticality.metrics.service;

import lombok.extern.slf4j.Slf4j;
import org.component.criticality.metrics.persistence.domain.Metric;
import org.component.criticality.metrics.persistence.repository.MetricRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
class MetricNormalizationService {

    private MetricRepository metricRepository;

    @Autowired
    public MetricNormalizationService(final MetricRepository metricRepository) {
        this.metricRepository = metricRepository;
    }

    void normalizeMetricValues() {
        log.info("Starting to normalize metrics for all architectures.");

        final Map<Long, List<Metric>> metricsByArchitecture = groupByArchitecture(metricRepository.findAll());
        metricsByArchitecture.values().forEach(this::normalizeArchitectureMetricValues);

        log.info("All metrics values have been normalized.");
    }

    private void normalizeArchitectureMetricValues(final List<Metric> architectureMetrics) {
        final Map<String, List<Metric>> metricsByMetricName = groupByMetricName(architectureMetrics);

        metricsByMetricName.values().forEach(metrics -> metricRepository.save(normalizeMetricValues(metrics)));
    }

    static List<Metric> normalizeMetricValues(List<Metric> metrics) {
        final double minValue = metrics.stream()
                .map(Metric::getValue)
                .min(Double::compare)
                .orElse(Double.MIN_VALUE);

        final double maxValue = metrics.stream()
                .map(Metric::getValue)
                .max(Double::compare)
                .orElse(Double.MAX_VALUE);

        metrics.forEach(metric -> metric.setNormalizedValue(normalizedValue(metric.getValue(), minValue, maxValue)));

        return metrics;
    }

    static Map<String, List<Metric>> groupByMetricName(final List<Metric> metrics) {
        return metrics.stream().collect(Collectors.groupingBy(Metric::getName));
    }

    static Map<Long, List<Metric>> groupByArchitecture(final List<Metric> metrics) {
        return metrics.stream().collect(Collectors.groupingBy(Metric::getArchitectureId));
    }

    static Long normalizedValue(final Double value, final Double min, final Double max) {
        final Double normalizedDouble = (value - min) / (max - min) * 100;
        return normalizedDouble.longValue();
    }
}
