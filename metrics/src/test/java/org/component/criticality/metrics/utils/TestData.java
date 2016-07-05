package org.component.criticality.metrics.utils;

import org.component.criticality.metrics.rest.client.domain.Architecture;
import org.component.criticality.metrics.rest.client.domain.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestData {

    /*
    * Dependency Graph of the sample Architecture stack:
    *
    * D <-----  B
    * ^  \      ^
    * |    \    |
    * |      \> |
    * E <-----> A <----- C
    *
    * (A -> B, E)
    * (B -> D)
    * (C -> A)
    * (D -> A)
    * (E -> A, D)
     */
    public static Architecture architectureWithCyclesInDependencyGraph() {
        final Architecture architecture = new Architecture("Mini Architecture");

        final Service serviceA = new Service(1L, "ServiceA");
        final Service serviceB = new Service(2L, "ServiceB");
        final Service serviceC = new Service(3L, "ServiceC");
        final Service serviceD = new Service(4L, "ServiceD");
        final Service serviceE = new Service(5L, "ServiceE");


        serviceA.getDependencies().addAll(Arrays.asList(2L, 5L));
        serviceB.getDependencies().addAll(Collections.singletonList(4L));
        serviceC.getDependencies().addAll(Collections.singletonList(1L));
        serviceD.getDependencies().addAll(Collections.singletonList(1L));
        serviceE.getDependencies().addAll(Arrays.asList(1L, 4L));

        final List<Service> services = new ArrayList<>(Arrays.asList(serviceA, serviceB, serviceC, serviceD, serviceE));
        architecture.getServices().addAll(services);

        return architecture;
    }

    public static Architecture architectureWithoutCyclesInDependencyGraph() {
        final Architecture architecture = new Architecture("Architecture without cycles in dependency graph");

        final Service serviceA = new Service(1L, "ServiceA");
        final Service serviceB = new Service(2L, "ServiceB");

        serviceB.getDependencies().addAll(Collections.singletonList(1L));

        final List<Service> services = new ArrayList<>(Arrays.asList(serviceA, serviceB));
        architecture.getServices().addAll(services);

        return architecture;
    }
}
