package org.component.criticality.metrics.calculations;

import org.component.criticality.metrics.rest.client.domain.Architecture;

import java.util.Map;

class Degree extends AbstractCalculator {

    final private static String NAME = "degree";
    final private static Double INITIAL_VALUE = 0.0;

    Degree(Architecture architecture) {
        super(architecture);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    Map<Long, Double> doCalculate() {
        final Map<Long, Double> servicesDegrees = initialMetrics(INITIAL_VALUE);
        final Map<Long, Double> inDegreeResults = new InDegree(architecture).doCalculate();
        final Map<Long, Double> outDegreeResults = new OutDegree(architecture).doCalculate();

        servicesDegrees.entrySet().forEach(e ->
                e.setValue(inDegreeResults.get(e.getKey()) + outDegreeResults.get(e.getKey()))
        );
        return servicesDegrees;
    }
}
