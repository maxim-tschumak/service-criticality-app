package org.component.criticality.metrics.calculations;

import org.junit.Test;

import java.util.Map;

import static org.component.criticality.metrics.utils.TestData.architectureWithoutCyclesInDependencyGraph;
import static org.component.criticality.metrics.utils.TestData.architectureWithCyclesInDependencyGraph;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class DegreeTest {

    @Test
    public void degreeTest() throws Exception {
        final Degree degreeCalculator = new Degree(architectureWithCyclesInDependencyGraph());

        final Map<Long, Double> results = degreeCalculator.doCalculate();

        assertThat(results.get(1L), equalTo(5.0));
        assertThat(results.get(2L), equalTo(2.0));
        assertThat(results.get(3L), equalTo(1.0));
        assertThat(results.get(4L), equalTo(3.0));
        assertThat(results.get(5L), equalTo(3.0));
    }

    @Test
    public void degreeInCycleFreeDependencyGraphTest() throws Exception {
        final Degree degreeCalculator = new Degree(architectureWithoutCyclesInDependencyGraph());

        final Map<Long, Double> results = degreeCalculator.doCalculate();

        assertThat(results.get(1L), equalTo(1.0));
        assertThat(results.get(2L), equalTo(1.0));
    }
}
