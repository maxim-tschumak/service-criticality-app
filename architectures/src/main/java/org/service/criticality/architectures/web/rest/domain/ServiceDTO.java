package org.service.criticality.architectures.web.rest.domain;

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
public class ServiceDTO {
    private Long id;
    private Long architectureId;
    private String name;
    private String description;
    private List<Long> dependencies = new ArrayList<>();
}
