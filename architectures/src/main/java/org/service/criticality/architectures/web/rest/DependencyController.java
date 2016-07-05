package org.service.criticality.architectures.web.rest;

import org.service.criticality.architectures.service.DependencyManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping(path = "/architectures/{architectureId}/services/{serviceId}/dependencies")
public class DependencyController {

    private DependencyManagement dependencyManagement;

    @Autowired
    public DependencyController(final DependencyManagement dependencyManagement){
        this.dependencyManagement = dependencyManagement;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Long> getDependencies(@PathVariable final Long serviceId) {
        return dependencyManagement.getDependenciesOfAService(serviceId);
    }

    @RequestMapping(path = "/{dependsOnServiceId}", method = RequestMethod.POST)
    public ResponseEntity<?> addNewDependencyToArchitecture(@PathVariable final Long architectureId,
                                                            @PathVariable final Long serviceId,
                                                            @PathVariable final Long dependsOnServiceId) {
        dependencyManagement
                .createDependencyWithinArchitecture(architectureId, serviceId, dependsOnServiceId);

        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().build().toUri());
        return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(path = "/{dependsOnServiceId}", method = RequestMethod.DELETE)
    public void deleteDependency(@PathVariable final Long architectureId,
                              @PathVariable final Long serviceId,
                              @PathVariable final Long dependsOnServiceId) {
        dependencyManagement.deleteDependency(architectureId, serviceId, dependsOnServiceId);
    }
}
