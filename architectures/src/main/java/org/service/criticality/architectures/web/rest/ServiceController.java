package org.service.criticality.architectures.web.rest;

import org.service.criticality.architectures.service.ServiceManagement;
import org.service.criticality.architectures.web.rest.domain.ServiceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping(path = "/architectures/{architectureId}/services")
public class ServiceController {

    private ServiceManagement serviceManagement;

    @Autowired
    public ServiceController(final ServiceManagement serviceManagement){
        this.serviceManagement = serviceManagement;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<ServiceDTO> getAllServicesOfArchitecture(@PathVariable final Long architectureId) {
        return serviceManagement.getServicesByArchitectureId(architectureId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> addNewServiceToArchitecture(@PathVariable final Long architectureId, @RequestBody final ServiceDTO service) {
        final ServiceDTO newService = serviceManagement.createServiceWithinArchitecture(service, architectureId);
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newService.getId()).toUri());
        return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(path = "/{serviceId}", method = RequestMethod.DELETE)
    public void deleteService(@PathVariable final Long architectureId, @PathVariable final Long serviceId) {
        serviceManagement.deleteService(architectureId, serviceId);
    }
}
