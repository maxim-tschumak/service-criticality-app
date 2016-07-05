package org.component.criticality.metrics.calculations;

import org.component.criticality.metrics.rest.client.domain.Architecture;
import org.component.criticality.metrics.rest.client.domain.Service;

import java.util.Map;
import java.util.stream.Collectors;

class OutDegree extends AbstractCalculator {

    private static final String NAME = "out-degree";

    OutDegree(Architecture architecture) {
        super(architecture);
    }

    @Override
    Map<Long, Double> doCalculate() {
        return architecture.getServices().stream().collect(Collectors.toMap(
                Service::getId,
                service -> (double) service.getDependencies().size()
        ));
    }

    @Override
    public String getName() {
        return NAME;
    }
}
