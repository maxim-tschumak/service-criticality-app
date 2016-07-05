package org.component.criticality.metrics.calculations;

import org.junit.Test;

import java.util.Map;

import static org.component.criticality.metrics.utils.TestData.architectureWithCyclesInDependencyGraph;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class OutDegreeTest {

    @Test
    public void outDegreeTest() throws Exception {
        final OutDegree outDegreeCalculator = new OutDegree(architectureWithCyclesInDependencyGraph());

        final Map<Long, Double> results = outDegreeCalculator.doCalculate();

        assertThat(results.get(1L), equalTo(2.0));
        assertThat(results.get(2L), equalTo(1.0));
        assertThat(results.get(3L), equalTo(1.0));
        assertThat(results.get(4L), equalTo(1.0));
        assertThat(results.get(5L), equalTo(2.0));
    }
}
