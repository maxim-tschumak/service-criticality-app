package org.component.criticality.metrics.calculations;

import org.component.criticality.metrics.rest.client.domain.Architecture;

import java.util.Map;

class KatzCentrality extends AbstractCalculator {

    final private static String NAME = "katz-centrality";

    final private static int MAX_ITERATIONS = 1000;
    final private static Double BETA = 1.0;
    final private static Double ALPHA = 0.5;

    KatzCentrality(Architecture architecture) {
        super(architecture);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    Map<Long, Double> doCalculate() {
        return katzCentrality(initialMetrics(BETA), 0);
    }

    private Map<Long, Double> katzCentrality(final Map<Long, Double> prevKatzCentrality, int iteration) {
        final Map<Long, Double> currKatyCentrality = initialMetrics(BETA);

        currKatyCentrality.entrySet().forEach(serviceKatzCentrality -> {
            final long serviceId = serviceKatzCentrality.getKey();
            currKatyCentrality.put(serviceId, ALPHA * valueOfIngoingVertices(serviceId, prevKatzCentrality) + BETA);
        });

        if (iteration++ == MAX_ITERATIONS || currKatyCentrality.equals(prevKatzCentrality)) {
            return currKatyCentrality;
        } else {
            return katzCentrality(currKatyCentrality, iteration);
        }
    }

    private Double valueOfIngoingVertices(final Long serviceId, final Map<Long, Double> prevValues) {
        return architecture.getServices().stream()
                .map(service -> service.getDependencies().contains(serviceId) ? prevValues.get(service.getId()) : 0.0)
                .reduce(0.0, Double::sum);
    }
}
