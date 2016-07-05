package org.component.criticality.metrics.persistence.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(schema = "metrics_data")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Metric {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long serviceId;
    private Long architectureId;
    private Double value;
    private Long normalizedValue;
    private String additionalInformation;
}
