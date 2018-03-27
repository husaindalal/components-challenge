package io.tenable.service;

import io.tenable.client.DigitalOceanClient;
import io.tenable.client.dto.DOComponent;
import io.tenable.dto.Component;
import io.tenable.metrics.Metrics;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Log4j2
public class ComponentService {

    private static final Set<String> VALID_STATUSES = Stream.of("operational",
            "degraded_performance", "partial_outage", "major_outage").collect(Collectors.toSet()); //Can be enums too
    private DigitalOceanClient digitalOceanClient;
    private ComponentEntityService componentEntityService;

    @Autowired
    public ComponentService(ComponentEntityService componentEntityService, DigitalOceanClient digitalOceanClient) {
        this.digitalOceanClient = digitalOceanClient;
        this.componentEntityService = componentEntityService;
    }

    private static boolean validateAndFilter(DOComponent doComponent, Set<String> names) {

        return VALID_STATUSES.contains(doComponent.getStatus())
                && (doComponent.getGroupId() != null && !doComponent.getGroupId().isEmpty())
                && doComponent.getName() != null
                && (names == null || names.contains(doComponent.getName().toLowerCase()));

    }

    private static Component transform(DOComponent doComponent) {
        Component component = new Component();
        component.setCompositeId(doComponent.getPageId() + doComponent.getGroupId());
        component.setName(doComponent.getName());
        component.setStatus(doComponent.getStatus());
        return component;
    }

    /**
     * Primary method to retrieve components from DigitalOcean api
     *
     * @param names Set of unique names to be retrieved. If none provided, all names will be returned.
     * @return
     */
    public List<Component> getComponents(Set<String> names) {
        //Log the count and size of array
        log.info("Get Components called with params {} ", names);
        List<Component> components = digitalOceanClient.getDoComponents().stream()
                .filter(comp -> validateAndFilter(comp, names))
                .map(ComponentService::transform)
                .collect(Collectors.toList());

        componentEntityService.save(components);

        Metrics.add("getComponents", (long) components.size(), names);
        log.info("Components retrieved for params {}: {} ", names, components.size());
        return components;
    }

    /**
     * Optional method to retrieve components from database
     */
    public List<Component> getDatabaseComponents() {

        return componentEntityService.getAll();
    }


}
