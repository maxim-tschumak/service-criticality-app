package org.component.criticality.metrics.rest;

import org.component.criticality.metrics.rest.domain.MetricDTO;
import org.component.criticality.metrics.service.MetricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping(path = "/metrics")
public class MetricController {

    private MetricService metricService;

    @Autowired
    public MetricController(final MetricService metricService) {
        this.metricService = metricService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<MetricDTO> getMetricsByServiceId(@RequestParam(value = "service_id", required = false) final Long serviceId,
                                                 @RequestParam(value = "architecture_id", required = false) final Long architectureId) {
        return metricService.getMetricsByServiceIdOrArchitectureId(serviceId, architectureId);
    }

    @RequestMapping(path = "/{metricId}", method = RequestMethod.GET)
    public MetricDTO getMetric(@PathVariable final Long metricId) {
        return metricService.getMetric(metricId);
    }

    @RequestMapping(path = "/{metricId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void putMetric(@PathVariable final Long metricId, @RequestBody final MetricDTO metricDTO) {
        metricDTO.setId(metricId);
        metricService.updateMetric(metricDTO);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> postMetric(@RequestBody final MetricDTO metricDTO) {
        final Long metricId = metricService.createMetric(metricDTO);

        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(metricId).toUri());
        return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
    }
}
