package org.component.criticality.metrics.calculations;

import org.junit.Test;

import java.util.Map;

import static org.component.criticality.metrics.utils.TestData.architectureWithoutCyclesInDependencyGraph;
import static org.component.criticality.metrics.utils.TestData.architectureWithCyclesInDependencyGraph;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class CyclesTest {

    @Test
    public void cyclesTest() throws Exception {
        final Cycles cyclesCalculator = new Cycles(architectureWithCyclesInDependencyGraph());

        final Map<Long, Double> results = cyclesCalculator.doCalculate();

        assertThat(results.get(1L), equalTo(3.0));
        assertThat(results.get(2L), equalTo(1.0));
        assertThat(results.get(3L), equalTo(0.0));
        assertThat(results.get(4L), equalTo(2.0));
        assertThat(results.get(5L), equalTo(2.0));
    }

    @Test
    public void cyclesInCycleFreeDependencyGraphTest() throws Exception {
        final Cycles cyclesCalculator = new Cycles(architectureWithoutCyclesInDependencyGraph());

        final Map<Long, Double> results = cyclesCalculator.doCalculate();

        assertThat(results.get(1L), equalTo(0.0));
        assertThat(results.get(2L), equalTo(0.0));
    }

    @Test
    public void additionalInformationTest() throws Exception {
        final Cycles cyclesCalculator = new Cycles(architectureWithCyclesInDependencyGraph());
        cyclesCalculator.doCalculate();

        final Map<Long, String> additionalInformation = cyclesCalculator.getAdditionalInformation();

        assertThat(additionalInformation.get(2L), equalTo("[ ServiceB ServiceD ServiceA ServiceB ]"));
    }
}
