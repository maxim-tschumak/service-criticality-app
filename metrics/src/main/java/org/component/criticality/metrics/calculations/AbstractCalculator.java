package org.component.criticality.metrics.calculations;

import org.component.criticality.metrics.rest.client.domain.Architecture;
import org.component.criticality.metrics.rest.client.domain.Service;

import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractCalculator {

    Architecture architecture;

    private final static String EMPTY_STRING = "";

    AbstractCalculator(final Architecture architecture) {
        this.architecture = architecture;
    }

    /**
     * @return Calculation results for all services. Key of the map is the service id. CalculationResults object
     * consists of original metric value, normalized value and additional information.
     */
    public Map<Long, CalculationResults> calculate() {
        final Map<Long, Double> calculatedValues = doCalculate();
        final Map<Long, String> additionalInformation = getAdditionalInformation();

        return combineResults(calculatedValues, additionalInformation);
    }

    /**
     * @return Name of the metric
     */
    public abstract String getName();

    /**
     * @return Metrics of the services. Key is the services id. Value ist the metric's value for the service.
     */
    abstract Map<Long, Double> doCalculate();

    /**
     * This method should be called only after calculations are done
     *
     * @return Map of additional information about service's metric value
     */
    Map<Long, String> getAdditionalInformation() {
        return architecture.getServices().stream().collect(Collectors.toMap(
                Service::getId,
                s -> EMPTY_STRING
        ));
    }

    Map<Long, Double> initialMetrics(final Double initialValue) {
        return architecture.getServices().stream().collect(Collectors.toMap(
                Service::getId,
                s -> initialValue
        ));
    }

    private static Map<Long, CalculationResults> combineResults(final Map<Long, Double> metricValuesMap,
                                                                final Map<Long, String> additionalInformation) {
        return metricValuesMap.entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                e -> new CalculationResults(e.getValue(), null, additionalInformation.get(e.getKey()))
        ));
    }
}
