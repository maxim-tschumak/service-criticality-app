package org.component.criticality.metrics.calculations;

import org.junit.Test;

import java.util.Map;

import static org.component.criticality.metrics.utils.TestData.architectureWithoutCyclesInDependencyGraph;
import static org.component.criticality.metrics.utils.TestData.architectureWithCyclesInDependencyGraph;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class ConstraintTest {

    @Test
    public void constraintTest() throws Exception {
        final Constraint constraintCalculator = new Constraint(architectureWithCyclesInDependencyGraph());

        final Map<Long, Double> results = constraintCalculator.doCalculate();

        assertThat(results.get(1L), equalTo(2.0));
        assertThat(results.get(2L), equalTo(1.0));
        assertThat(results.get(3L), equalTo(1.0));
        assertThat(results.get(4L), equalTo(1.0));
        assertThat(results.get(5L), equalTo(5.0));
    }

    @Test
    public void constraintInCycleFreeDependencyGraphTest() throws Exception {
        final Constraint constraintCalculator = new Constraint(architectureWithoutCyclesInDependencyGraph());

        final Map<Long, Double> results = constraintCalculator.doCalculate();

        assertThat(results.get(1L), equalTo(0.0));
        assertThat(results.get(2L), equalTo(1.0));
    }
}
