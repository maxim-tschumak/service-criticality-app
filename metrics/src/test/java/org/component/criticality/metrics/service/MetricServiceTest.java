package org.component.criticality.metrics.service;

import org.component.criticality.metrics.persistence.domain.Metric;
import org.component.criticality.metrics.persistence.repository.MetricRepository;
import org.component.criticality.metrics.rest.domain.MetricDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.stub;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class MetricServiceTest {

    private MetricService metricService;

    @Mock
    private MetricRepository metricRepository;

    @Before
    public void setUp() {
        initMocks(this);
        metricService = new MetricService(metricRepository);
    }

    @Test
    public void getAllMetricsTest() throws Exception {
        metricService.getMetricsByServiceIdOrArchitectureId(null, null);

        verify(metricRepository).findAll();
    }

    @Test
    public void getMetricsByServiceIdTest() throws Exception {
        metricService.getMetricsByServiceIdOrArchitectureId(1L, null);

        verify(metricRepository).findByServiceId(1L);
    }

    @Test
    public void getMetricsByArchitectureIdTest() throws Exception {
        metricService.getMetricsByServiceIdOrArchitectureId(null, 1L);

        verify(metricRepository).findByArchitectureIdAndNormalizedValueNotNull(1L);
    }

    @Test
    public void getMetricsReturnsListOfMetricDTOsTest() throws Exception {
        stub(metricRepository.findAll()).toReturn(testMetrics());

        final List<MetricDTO> metricDTOs = metricService.getMetricsByServiceIdOrArchitectureId(null, null);

        assertThat(metricDTOs.size(), equalTo(2));
    }

    @Test
    public void createMetricTest() throws Exception {
        stub(metricRepository.save(any(Metric.class))).toReturn(testCoverageMetric());

        metricService.createMetric(new MetricDTO());

        verify(metricRepository).save(any(Metric.class));
    }

    @Test
    public void updateMetricTest() throws Exception {
        stub(metricRepository.findOne(1L)).toReturn(testCoverageMetric());

        metricService.updateMetric(MetricDTO.builder().id(1L).build());

        verify(metricRepository).findOne(1L);
        verify(metricRepository).save(any(Metric.class));
    }

    @Test
    public void getMetricTest() throws Exception {
        stub(metricRepository.findOne(1L)).toReturn(inDegreeMetric());

        final MetricDTO metricDTO = metricService.getMetric(1L);

        verify(metricRepository).findOne(1L);
        assertThat(metricDTO.getName(), equalTo(inDegreeMetric().getName()));
        assertThat(metricDTO.getValue(), equalTo(inDegreeMetric().getValue()));
    }

    private List<Metric> testMetrics() {
        final List<Metric> metrics = new ArrayList<>();
        metrics.add(testCoverageMetric());
        metrics.add(inDegreeMetric());

        return metrics;
    }

    private Metric testCoverageMetric() {
        return Metric.builder()
                .id(1L).name("test-coverage")
                .serviceId(1L)
                .value(0.9)
                .normalizedValue(90L)
                .build();
    }

    private Metric inDegreeMetric() {
        return Metric.builder()
                .id(1L)
                .name("in-degree")
                .serviceId(1L)
                .value(10.0)
                .normalizedValue(2L)
                .build();
    }
}
