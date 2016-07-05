package org.component.criticality.metrics.calculations;

import org.component.criticality.metrics.rest.client.domain.Architecture;
import org.component.criticality.metrics.rest.client.domain.Service;

import java.util.*;
import java.util.stream.Collectors;

class Cycles extends AbstractCalculator {

    private static final String NAME = "cycles";
    private static final Map<Long, String> additionalInformation = new HashMap<>();

    Cycles(Architecture architecture) {
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
                this::numberOfMinimalCycles
        ));
    }

    @Override
    Map<Long, String> getAdditionalInformation() {
        return additionalInformation;
    }

    private double numberOfMinimalCycles(final Service service) {
        final Set<List<Long>> cycles = traverse(service.getId(), new ArrayList<>(), new HashSet<>());
        collectAdditionalInformation(service.getId(), cycles);
        return cycles.size();
    }

    private Set<List<Long>> traverse(final Long currentService, final List<Long> visitedServices, final Set<List<Long>> cyclesAcc) {
        final List<Long> copyOfVisitedServices = new ArrayList<>(visitedServices);
        copyOfVisitedServices.add(currentService);

        /* there is a cycle in visited services */
        if (visitedServices.contains(currentService)) {
            /* current service (position) is part of the cycle */
            if (visitedServices.get(0).equals(currentService)) {
                cyclesAcc.add(copyOfVisitedServices);
            }
        } else /* let's go deeper and investigate the dependencies */ {
            dependenciesOf(currentService).forEach(d -> traverse(d, copyOfVisitedServices, cyclesAcc));
        }

        return cyclesAcc;
    }

    private List<Long> dependenciesOf(final Long currentService) {
        return architecture.getServices().stream()
                .filter(s -> s.getId().longValue() == currentService)
                .findFirst().orElseGet(null)
                .getDependencies();
    }

    private void collectAdditionalInformation(final Long id, final Set<List<Long>> cycles) {
        final StringBuilder stringBuilder = new StringBuilder("");
        cycles.forEach(cycle -> {
            stringBuilder.append("[ ");
            cycle.forEach(serviceId -> {
                stringBuilder.append(serviceName(serviceId));
                stringBuilder.append(" ");
            });
            stringBuilder.append("]");
        });
        additionalInformation.put(id, stringBuilder.toString());
    }

    private String serviceName(final Long serviceId) {
        return architecture.getServices().stream()
                .filter(service -> service.getId().longValue() == serviceId)
                .findFirst().orElseGet(null)
                .getName();
    }
}