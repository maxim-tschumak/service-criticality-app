package org.component.criticality.metrics.service;

import lombok.extern.slf4j.Slf4j;
import org.component.criticality.metrics.persistence.domain.Metric;
import org.component.criticality.metrics.rest.domain.MetricDTO;
import org.component.criticality.metrics.persistence.repository.MetricRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MetricService {

    private MetricRepository metricRepository;

    @Autowired
    public MetricService(final MetricRepository metricRepository) {
        this.metricRepository = metricRepository;
    }

    public List<MetricDTO> getMetricsByServiceIdOrArchitectureId(final Long serviceId, final Long architectureId) {
        List<Metric> metrics;
        if (serviceId != null) {
            metrics = metricRepository.findByServiceId(serviceId);
        } else if (architectureId != null) {
            metrics = metricRepository.findByArchitectureIdAndNormalizedValueNotNull(architectureId);
        } else {
            metrics = metricRepository.findAll();
        }

        return metrics.stream().map(MetricService::fromMetric).collect(Collectors.toList());
    }


    public Long createMetric(final MetricDTO metricDTO) {
        final Metric metric = metricRepository.save(fromMetricDTO(metricDTO));
        log.info("New Metric has been stored {}", metric);
        return metric.getId();
    }

    public void updateMetric(final MetricDTO metricDTO) {
        final Metric metric = metricRepository.findOne(metricDTO.getId());
        metric.setValue(metricDTO.getValue());
        metric.setNormalizedValue(metricDTO.getNormalizedValue());
        metric.setAdditionalInformation(metricDTO.getAdditionalInformation());
        metricRepository.save(metric);

        log.info("Metric has been updated {}", metric);
    }

    public MetricDTO getMetric(Long id) {
        final Metric metric = metricRepository.findOne(id);
        return fromMetric(metric);
    }

    private static MetricDTO fromMetric(final Metric metric) {
        final MetricDTO metricDTO = new MetricDTO();
        metricDTO.setId(metric.getId());
        metricDTO.setName(metric.getName());
        metricDTO.setServiceId(metric.getServiceId());
        metricDTO.setArchitectureId(metric.getArchitectureId());
        metricDTO.setValue(metric.getValue());
        metricDTO.setNormalizedValue(metric.getNormalizedValue());
        metricDTO.setAdditionalInformation(metric.getAdditionalInformation());

        return metricDTO;
    }

    private static Metric fromMetricDTO(final MetricDTO metricDTO) {
        final Metric metric = new Metric();
        metric.setId(metricDTO.getId());
        metric.setName(metricDTO.getName());
        metric.setServiceId(metricDTO.getServiceId());
        metric.setArchitectureId(metricDTO.getArchitectureId());
        metric.setValue(metricDTO.getValue());
        metric.setNormalizedValue(metricDTO.getNormalizedValue());
        metric.setAdditionalInformation(metricDTO.getAdditionalInformation());

        return metric;
    }
}
