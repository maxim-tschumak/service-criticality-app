package org.component.criticality.metrics.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class ScheduledTaskService {

    private static final String EVERY_TWO_MINUTES = "0 */2 * * * *";

    @Autowired
    private MetricCalculationService metricCalculationService;

    @Autowired
    private MetricNormalizationService metricNormalizationService;

    @Scheduled(cron = EVERY_TWO_MINUTES)
    public void calculateMetricsAndNormalizeValues() {
        metricCalculationService.calculateMetrics();
        metricNormalizationService.normalizeMetricValues();
    }
}
