package org.component.criticality.metrics.calculations;

import org.junit.Test;

import java.util.Map;

import static org.component.criticality.metrics.utils.TestData.architectureWithCyclesInDependencyGraph;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class InDegreeTest {

    @Test
    public void inDegreeTest() throws Exception {
        final InDegree inDegreeCalculator = new InDegree(architectureWithCyclesInDependencyGraph());

        final Map<Long, Double> results = inDegreeCalculator.doCalculate();

        assertThat(results.get(1L), equalTo(3.0));
        assertThat(results.get(2L), equalTo(1.0));
        assertThat(results.get(3L), equalTo(0.0));
        assertThat(results.get(4L), equalTo(2.0));
        assertThat(results.get(5L), equalTo(1.0));
    }
}
