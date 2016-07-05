package org.component.criticality.metrics.service;

import org.component.criticality.metrics.persistence.domain.Metric;
import org.junit.Test;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.component.criticality.metrics.service.MetricNormalizationService.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class MetricNormalizationServiceTest {

    @Test
    public void calculateNormalizedValueTest() throws Exception {
        final Long normalizedValue = normalizedValue(2.0, 1.0, 11.0);

        assertThat(normalizedValue, equalTo(10L));
    }

    @Test
    public void metricsByArchitectureTest() throws Exception {
        final Map<Long, List<Metric>> groupedByArchitectureId = groupByArchitecture(sampleMetrics());

        assertThat(groupedByArchitectureId.get(1L).size(), equalTo(3));
        assertThat(groupedByArchitectureId.get(2L).size(), equalTo(1));
    }

    @Test
    public void groupByMetricNameTest() throws Exception {
        final Map<String, List<Metric>> groupedByName = groupByMetricName(sampleMetrics());

        assertThat(groupedByName.get("degree").size(), equalTo(3));
        assertThat(groupedByName.get("katz-centrality").size(), equalTo(1));
    }

    @Test
    public void normalizeMetricValuesTest() throws Exception {
        final List<Metric> normalizedMetrics = normalizeMetricValues(degreeMetrics());

        normalizedMetrics.forEach(metric -> {
            assertThat(metric.getNormalizedValue(), notNullValue());
            switch (metric.getServiceId().intValue()) {
                case 1:
                    assertThat(metric.getNormalizedValue(), equalTo(0L));
                    break;
                case 2:
                    assertThat(metric.getNormalizedValue(), equalTo(25L));
                    break;
                case 3:
                    assertThat(metric.getNormalizedValue(), equalTo(100L));
                    break;
            }
        });
    }

    private List<Metric> sampleMetrics() {
        final List<Metric> metrics = new ArrayList<>();
        metrics.add(Metric.builder().architectureId(1L).name("degree").build());
        metrics.add(Metric.builder().architectureId(1L).name("degree").build());
        metrics.add(Metric.builder().architectureId(1L).name("katz-centrality").build());
        metrics.add(Metric.builder().architectureId(2L).name("degree").build());

        return metrics;
    }

    private List<Metric> degreeMetrics() {
        final List<Metric> metrics = new ArrayList<>();
        metrics.add(Metric.builder().serviceId(1L).name("degree").value(1.0).build());
        metrics.add(Metric.builder().serviceId(2L).name("degree").value(2.0).build());
        metrics.add(Metric.builder().serviceId(3L).name("degree").value(5.0).build());

        return metrics;
    }
}
