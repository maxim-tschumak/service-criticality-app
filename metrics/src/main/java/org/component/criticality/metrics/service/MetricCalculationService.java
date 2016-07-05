package org.component.criticality.metrics.service;

import lombok.extern.slf4j.Slf4j;
import org.component.criticality.metrics.calculations.*;
import org.component.criticality.metrics.persistence.domain.Metric;
import org.component.criticality.metrics.persistence.repository.MetricRepository;
import org.component.criticality.metrics.rest.client.ArchitectureStacksClient;
import org.component.criticality.metrics.rest.client.domain.Architecture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
class MetricCalculationService {

    @Autowired
    private MetricRepository metricRepository;

    @Autowired
    private ArchitectureStacksClient architectureStacksClient;

    void calculateMetrics() {
        log.info("Starting to calculate the metrics.");

        architectureStacksClient.getAll().forEach(this::analyseArchitecture);

        log.info("All architectures stacks have been analysed and the metrics have been updated.");
    }

    private void analyseArchitecture(Architecture architecture) {
        /* initialize calculator factory */
        final CalculatorFactory factory = new CalculatorFactory(architecture);

        /* calculate the results */
        final List<AbstractCalculator> calculators = Arrays.asList(
                factory.inDegree(),
                factory.outDegree(),
                factory.degree(),
                factory.katzCentrality(),
                factory.effectiveSize(),
                factory.constraint(),
                factory.cycles()
        );

        Map<Long, CalculationResults> results;
        for (AbstractCalculator calculator : calculators) {
            results = calculator.calculate();
            /* convert and update or create new metrics */
            createOrUpdateMetrics(architecture.getId(), calculator.getName(), results);
        }
    }

    private void createOrUpdateMetrics(final Long architectureId, final String metricName,
                                       final Map<Long, CalculationResults> serviceResults) {
        final List<Metric> metrics = serviceResults.entrySet().stream()
                .map(serviceIdAndResults -> metric(serviceIdAndResults, architectureId, metricName))
                .collect(Collectors.toList());

        metricRepository.save(metrics);
    }

    private Metric metric(final Map.Entry<Long, CalculationResults> serviceIdAndResults, final Long architectureId,
                          final String metricName) {
        final Long serviceId = serviceIdAndResults.getKey();
        final CalculationResults result = serviceIdAndResults.getValue();

        final Optional<Metric> storedMetric = metricRepository
                .findByArchitectureIdAndServiceIdAndName(architectureId, serviceId, metricName);

        Metric metric;
        if (storedMetric.isPresent()) {
            metric = storedMetric.get();
        } else {
            metric = new Metric();
            metric.setArchitectureId(architectureId);
            metric.setServiceId(serviceId);
            metric.setName(metricName);
        }
        metric.setValue(result.getValue());
        metric.setNormalizedValue(result.getNormalizedValue());
        metric.setAdditionalInformation(result.getAdditionalInformation());

        return metric;
    }
}
