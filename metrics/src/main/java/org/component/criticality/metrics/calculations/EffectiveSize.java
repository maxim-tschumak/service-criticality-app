package org.component.criticality.metrics.calculations;

import org.component.criticality.metrics.rest.client.domain.Architecture;
import org.component.criticality.metrics.rest.client.domain.Service;

import java.util.Map;
import java.util.stream.Collectors;

class EffectiveSize extends AbstractCalculator {

    private static final String NAME = "effective-size";

    EffectiveSize(Architecture architecture) {
        super(architecture);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    Map<Long, Double> doCalculate() {
        return architecture.getServices().stream().collect(Collectors.toMap(
                Service::getId,
                this::effectiveSize
        ));
    }

    private double effectiveSize(final Service service) {
        /* number of services without the service itself */
        final double numberOfOtherServices = architecture.getServices().size() - 1;
        final double edgesNotConnectedToService = edgesNotConnectedToService(service.getId());

        return numberOfOtherServices - 2.0 * edgesNotConnectedToService / numberOfOtherServices;
    }

    long edgesNotConnectedToService(final Long serviceId) {
        return architecture.getServices().stream()
                .filter(s -> s.getId().longValue() != serviceId)
                .map(s -> dependenciesWithout(s, serviceId))
                .reduce(0L, (a, b) -> a + b);
    }

    /**
     * @return number of service's ({@code service}) dependencies not equal the given one ({@code dependencyId})
     */
    private long dependenciesWithout(final Service service, final Long dependencyId) {
        return service.getDependencies().stream().filter(dependency -> dependency.longValue() != dependencyId).count();
    }
}
