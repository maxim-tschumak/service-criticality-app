package org.component.criticality.metrics.rest.client.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Architecture {
    private Long id;
    private String name;
    private String description;
    private List<Service> services = new ArrayList<>();

    public Architecture(final String name){
        this.name = name;
    }
}
