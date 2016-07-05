package org.service.criticality.architectures.web.rest;

import org.service.criticality.architectures.service.ArchitectureManagement;
import org.service.criticality.architectures.web.rest.domain.ArchitectureDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping(path = "/architectures")
public class ArchitectureController {

    private ArchitectureManagement architectureManagement;

    @Autowired
    public ArchitectureController(final ArchitectureManagement architectureManagement) {
        this.architectureManagement = architectureManagement;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<ArchitectureDTO> getArchitectures(@RequestParam(value = "name", required = false) final String name) {
        final List<ArchitectureDTO> architectures;
        if (name != null) {
            architectures = architectureManagement.getArchitecturesByName(name);
        } else {
            architectures = architectureManagement.getAllArchitectures();
        }
        return architectures;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ArchitectureDTO getArchitecture(@PathVariable final Long id) {
        return architectureManagement.getArchitectureById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> postArchitecture(@RequestBody final ArchitectureDTO architecture) {
        final ArchitectureDTO newArchitecture = architectureManagement.createNewArchitecture(architecture);

        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newArchitecture.getId()).toUri());
        return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void deleteArchitecture(@PathVariable final Long id) {
        architectureManagement.deleteArchitecture(id);
    }
}
