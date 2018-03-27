package io.tenable.controller.v1;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.tenable.dto.Component;
import io.tenable.service.ComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping(value = "/api/v1/components")

public class ComponentController {

    private ComponentService componentService;

    @Autowired
    public ComponentController(ComponentService componentService) {
        this.componentService = componentService;
    }

    @ApiOperation(
            value = "Allows the caller to optionally specify a comma separated list of names, such as: GET /api/v1/components?name=API,Services\n" +
                    "If  name  is specified, then filter the JSON received from DO to only include  components where  component.name = <name specified in query string> ."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Showing all components that matched the criteria")
    })
    @GetMapping
    public List<Component> getComponents(@RequestParam(value = "name", required = false) String nameParam) {

        Set<String> names = getComponentNames(nameParam);

        return componentService.getComponents(names);
    }


    private Set<String> getComponentNames(String nameParam) {
        Set<String> names = null;
        if (nameParam != null && nameParam.trim().length() > 0) {
            names = Stream.of(nameParam.split(","))
                    .map(String::new)
                    .map(String::toLowerCase)
                    .filter(it -> it.trim().length() > 0)
                    .collect(Collectors.toSet());

        }
        return names;
    }

}
