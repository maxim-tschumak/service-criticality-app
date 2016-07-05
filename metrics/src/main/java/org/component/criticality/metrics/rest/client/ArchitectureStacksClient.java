package org.component.criticality.metrics.rest.client;

import org.component.criticality.metrics.rest.client.domain.Architecture;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@FeignClient(name="architecture-stacks", url = "${org.component.criticality.architecture-stacks.url}")
public interface ArchitectureStacksClient {
    @RequestMapping(value = "/architectures", method = GET)
    List<Architecture> getAll();
}
