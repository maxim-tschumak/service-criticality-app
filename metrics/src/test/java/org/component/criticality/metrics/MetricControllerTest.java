package org.component.criticality.metrics;

import org.component.criticality.metrics.rest.domain.MetricDTO;
import org.component.criticality.metrics.service.MetricService;
import org.component.criticality.metrics.rest.MetricController;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class MetricControllerTest {

    private MetricController metricController;

    @Mock
    private MetricService metricService;

    @Before
    public void setUp() {
        initMocks(this);

        metricController = new MetricController(metricService);
    }

    @Test
    public void getMetricsTest() throws Exception {
        metricController.getMetricsByServiceId(null, null);

        verify(metricService).getMetricsByServiceIdOrArchitectureId(null, null);
    }

    @Test
    public void getMetricTest() throws Exception {
        metricController.getMetric(null);

        verify(metricService).getMetric(null);
    }

    @Test
    public void putMetricTest() throws Exception {
        metricController.putMetric(1L, testCoverageMetricDTO());

        verify(metricService).updateMetric(testCoverageMetricDTO());
    }

    @Test
    public void postMetricTest() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final ResponseEntity response = metricController.postMetric(testCoverageMetricDTO());

        verify(metricService).createMetric(testCoverageMetricDTO());
        assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
    }

    private MetricDTO testCoverageMetricDTO() {
        return MetricDTO.builder()
                .id(1L)
                .name("test-coverage")
                .value(0.9)
                .build();
    }
}
