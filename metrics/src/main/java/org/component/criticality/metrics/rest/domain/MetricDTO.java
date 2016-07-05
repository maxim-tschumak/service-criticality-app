package org.component.criticality.metrics.rest.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MetricDTO {
    private Long id;
    private String name;
    private Long serviceId;
    private Long architectureId;
    private Double value;
    private Long normalizedValue;
    private String additionalInformation;
}
