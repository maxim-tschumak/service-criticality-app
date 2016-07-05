package org.component.criticality.metrics.calculations;

import org.junit.Test;

import java.util.Map;

import static org.component.criticality.metrics.utils.TestData.architectureWithoutCyclesInDependencyGraph;
import static org.component.criticality.metrics.utils.TestData.architectureWithCyclesInDependencyGraph;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class EffectiveSizeTest {

    @Test
    public void effectiveSizeTest() throws Exception {
        final EffectiveSize effectiveSizeCalculator = new EffectiveSize(architectureWithCyclesInDependencyGraph());

        final Map<Long, Double> results = effectiveSizeCalculator.doCalculate();

        assertThat(results.get(1L), equalTo(3.0));
        assertThat(results.get(2L), equalTo(1.5));
        assertThat(results.get(3L), equalTo(1.0));
        assertThat(results.get(4L), equalTo(2.0));
        assertThat(results.get(5L), equalTo(2.0));
    }

    @Test
    public void edgesNotConnectedToTest() throws Exception {
        final EffectiveSize effectiveSizeCalculator = new EffectiveSize(architectureWithCyclesInDependencyGraph());

        assertThat(effectiveSizeCalculator.edgesNotConnectedToService(1L),equalTo(2L));
        assertThat(effectiveSizeCalculator.edgesNotConnectedToService(2L),equalTo(5L));
        assertThat(effectiveSizeCalculator.edgesNotConnectedToService(3L),equalTo(6L));
        assertThat(effectiveSizeCalculator.edgesNotConnectedToService(4L),equalTo(4L));
        assertThat(effectiveSizeCalculator.edgesNotConnectedToService(5L),equalTo(4L));
    }

    @Test
    public void effectiveSizeInCycleFreeDependencyGraphTest() throws Exception {
        final EffectiveSize effectiveSizeCalculator = new EffectiveSize(architectureWithoutCyclesInDependencyGraph());

        final Map<Long, Double> results = effectiveSizeCalculator.doCalculate();

        assertThat(results.get(1L), equalTo(1.0));
        assertThat(results.get(2L), equalTo(1.0));
    }
}
