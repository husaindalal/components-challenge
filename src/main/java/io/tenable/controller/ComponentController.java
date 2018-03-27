package io.tenable.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.tenable.dto.Component;
import io.tenable.exception.CustomException;
import io.tenable.service.ComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/persons")

public class ComponentController {

    private ComponentService componentService;

    @Autowired
    public ComponentController(ComponentService componentService) {
        this.componentService = componentService;
    }

    @GetMapping
    @ApiOperation(
            value = "Find all persons. Used for testing only should not be exposed in production"
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Showing all people found")
    })
    public List<Component> findAll() {
        return componentService.findAll();
    }

    @GetMapping(value = "/{id}")
    public Component findById(@PathVariable(value = "id") Long personId) {

        Optional<Component> person = componentService.findById(personId);
        if (person.isPresent()) {
            return person.get();
        }

        throw new CustomException("Component with id " + personId + " is not present"); //Compiler will optimize the string concatenation
    }

    // Create a new Component
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Component create(@Valid @RequestBody Component component) {
        return componentService.save(component);
    }

    @PutMapping("/{id}")
    public Component update(@NotNull @PathVariable(value = "id") Long personId,
                            @Valid @RequestBody Component component) {

        if (component.getId() == null) {
            component.setId(personId);
        }
        if (!component.getId().equals(personId)) {
            throw new IllegalArgumentException("Invalid: personId does not match");
        }
        return componentService.save(component);
    }

    // Delete a Component
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long personId) {

        componentService.delete(personId);

        return ResponseEntity.ok().build();
    }
}
