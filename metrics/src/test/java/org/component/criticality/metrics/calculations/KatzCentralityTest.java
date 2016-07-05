package org.component.criticality.metrics.calculations;

import org.junit.Test;

import java.util.Map;

import static org.component.criticality.metrics.utils.TestData.architectureWithoutCyclesInDependencyGraph;
import static org.component.criticality.metrics.utils.TestData.architectureWithCyclesInDependencyGraph;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class KatzCentralityTest {

    @Test
    public void katzCentralityTest() throws Exception {
        final KatzCentrality katzCentrality = new KatzCentrality(architectureWithCyclesInDependencyGraph());

        final Map<Long, Double> results = katzCentrality.doCalculate();

        assertThat(results.get(1L), equalTo(6.0));
        assertThat(results.get(2L), equalTo(4.0));
        assertThat(results.get(3L), equalTo(1.0));
        assertThat(results.get(4L), equalTo(5.0));
        assertThat(results.get(5L), equalTo(4.0));
    }

    @Test
    public void katzCentralityInCycleFreeDependencyGraphTest() throws Exception {
        final KatzCentrality katzCentrality = new KatzCentrality(architectureWithoutCyclesInDependencyGraph());

        final Map<Long, Double> results = katzCentrality.doCalculate();

        assertThat(results.get(1L), equalTo(1.5));
        assertThat(results.get(2L), equalTo(1.0));
    }
}
