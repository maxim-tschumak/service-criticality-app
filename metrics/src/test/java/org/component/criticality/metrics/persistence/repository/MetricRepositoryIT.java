package org.component.criticality.metrics.persistence.repository;

import org.component.criticality.metrics.MetricsApp;
import org.component.criticality.metrics.persistence.domain.Metric;
import org.component.criticality.metrics.utils.DataSourceTestConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {DataSourceTestConfiguration.class, MetricsApp.class})
public class MetricRepositoryIT {

    @Autowired
    private MetricRepository metricRepository;

    @Before
    public void setUp() {
        metricRepository.deleteAll();
    }

    @Test
    public void findByArchitectureIdAndNormalizedValueNotNullTest() {
        metricRepository.save(sampleMetric("katz-centrality", 1L, 10L));
        metricRepository.save(sampleMetric("katz-centrality", 2L, 20L));
        metricRepository.save(sampleMetric("in-degree", 1L, null));

        assertThat(metricRepository.findByArchitectureIdAndNormalizedValueNotNull(0L).size(), equalTo(2));
    }

    @Test
    public void findByArchitectureIdAndServiceIdAndNameTest() {
        metricRepository.save(sampleMetric("constraint", 1L, 1L));
        metricRepository.save(sampleMetric("test-coverage", 1L, 1L));
        metricRepository.save(sampleMetric("test-coverage", 2L, 1L));

        assertThat(metricRepository.findByArchitectureIdAndServiceIdAndName(0L, 1L, "constraint").isPresent(), is(true));
    }

    public static Metric sampleMetric(final String name, final Long serviceId, final Long normalizedValue) {
        return Metric.builder()
                .serviceId(serviceId)
                .name(name)
                .value(0.2)
                .normalizedValue(normalizedValue)
                .architectureId(0L)
                .build();
    }
}
