package org.component.criticality.metrics.calculations;

import org.component.criticality.metrics.rest.client.domain.Architecture;
import org.component.criticality.metrics.rest.client.domain.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

class Constraint extends AbstractCalculator {

    private static final String NAME = "constraint";

    Constraint(Architecture architecture) {
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
                this::constraint
        ));
    }

    private double constraint(final Service service) {
        return service.getDependencies().stream()
                .map(dependency -> Math.pow(1 + numberOfConstraints(service, dependency), 2))
                .reduce(0.0, (a, b) -> a + b);
    }

    private double numberOfConstraints(final Service service, final Long dependency) {
        final List<Long> serviceDependencies = service.getDependencies();
        final Set<Long> servicesDependsOnDependency = servicesDependsOn(dependency);

        return servicesDependsOnDependency.stream().filter(serviceDependencies::contains).count();
    }

    private Set<Long> servicesDependsOn(final Long dependency) {
        final Set<Long> servicesDependsOnDependency = new HashSet<>();
        architecture.getServices().forEach(service -> {
            if (service.getDependencies().contains(dependency)) {
                servicesDependsOnDependency.add(service.getId());
            }
        });
        return servicesDependsOnDependency;
    }
}
