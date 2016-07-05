package org.component.criticality.metrics.calculations;

import org.component.criticality.metrics.rest.client.domain.Architecture;

import java.util.Map;

class InDegree extends AbstractCalculator {

    private static final String NAME = "in-degree";

    private static final Double INITIAL_VALUE = 0.0;

    InDegree(Architecture architecture) {
        super(architecture);
    }

    @Override
    Map<Long, Double> doCalculate() {
        final Map<Long, Double> servicesInDegrees = initialMetrics(INITIAL_VALUE);
        architecture.getServices().forEach(s ->
                s.getDependencies().forEach(d ->
                        servicesInDegrees.put(d, servicesInDegrees.get(d) + 1.0)
                ));
        return servicesInDegrees;
    }

    @Override
    public String getName() {
        return NAME;
    }
}
