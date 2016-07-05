package org.component.criticality.metrics.calculations;

import org.component.criticality.metrics.rest.client.domain.Architecture;

public class CalculatorFactory {

    private final Architecture architecture;

    public CalculatorFactory(final Architecture architecture) {
        this.architecture = architecture;
    }

    public InDegree inDegree() {
        return new InDegree(architecture);
    }

    public OutDegree outDegree() {
        return new OutDegree(architecture);
    }

    public Degree degree() {
        return new Degree(architecture);
    }

    public KatzCentrality katzCentrality() {
        return new KatzCentrality(architecture);
    }

    public EffectiveSize effectiveSize() {
        return new EffectiveSize(architecture);
    }

    public Constraint constraint() {
        return new Constraint(architecture);
    }

    public Cycles cycles() {
        return new Cycles(architecture);
    }
}
