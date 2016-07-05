package org.component.criticality.metrics.calculations;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CalculationResults {
    private Double value;
    private Long normalizedValue;
    private String additionalInformation;
}
