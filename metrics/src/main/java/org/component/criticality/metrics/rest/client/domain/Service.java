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
public class Service {
    private Long id;
    private Long architectureId;
    private String name;
    private String description;
    private List<Long> dependencies = new ArrayList<>();

    public Service(final Long id, final String name){
        this.id = id;
        this.name = name;
    }
}
