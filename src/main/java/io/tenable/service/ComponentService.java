package io.tenable.service;

import io.tenable.client.DigitalOceanClient;
import io.tenable.client.dto.DOComponent;
import io.tenable.dto.Component;
import io.tenable.entity.ComponentEntity;
import io.tenable.metrics.Metrics;
import io.tenable.repository.ComponentRepository;
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

    private ComponentRepository componentRepository;
    private static final Set<String> VALID_STATUSES = Stream.of("operational",
            "degraded_performance", "partial_outage", "major_outage").collect(Collectors.toSet()); //Can be enums too
    private DigitalOceanClient digitalOceanClient;

    @Autowired
    public ComponentService(ComponentRepository componentRepository, DigitalOceanClient digitalOceanClient) {
        this.componentRepository = componentRepository;
        this.digitalOceanClient = digitalOceanClient;
    }


    public List<Component> getComponents(Set<String> names) {
        //Log the count and size of array
        log.info("Get Components called with params {} ", names);
        List<Component> components = digitalOceanClient.getDoComponents().stream()
                .filter(comp -> validateAndFilter(comp, names))
                .map(this::transform)
                .collect(Collectors.toList());

        //TODO save asynchronously either using CompletableFuture or Publishing event

        Metrics.add("getComponents", (long) components.size(), names);
        log.info("Components retrieved for params {}: {} ", names, components.size());
        return components;
    }

    private boolean validateAndFilter(DOComponent doComponent, Set<String> names) {

        return VALID_STATUSES.contains(doComponent.getStatus())
                && (doComponent.getGroupId() != null && !doComponent.getGroupId().isEmpty())
                && doComponent.getName() != null
                && (names == null || names.contains(doComponent.getName().toLowerCase()));

    }

    private Component transform(DOComponent doComponent) {
        Component component = new Component();
        component.setCompositeId(doComponent.getPageId() + doComponent.getGroupId());
        component.setName(doComponent.getName());
        component.setStatus(doComponent.getStatus());
        return component;
    }

    /**
     * We can also move the mapper and repository interactions into a EntityService but it seemed to be overkill
     */
    static class Mapper {
        static Component toDto(ComponentEntity componentEntity) {
            Component component = new Component();
            component.setStatus(componentEntity.getStatus());
            component.setName(componentEntity.getName());
            component.setCompositeId(componentEntity.getCompositeId());

            return component;
        }

        static List<Component> toDto(List<ComponentEntity> personEntities) {
            return personEntities.stream().map(Mapper::toDto).collect(Collectors.toList());
        }

        static ComponentEntity toDao(Component component) {
            ComponentEntity componentEntity = new ComponentEntity();
            componentEntity.setStatus(component.getStatus());
            componentEntity.setName(component.getName());
            componentEntity.setCompositeId(component.getCompositeId());

            return componentEntity;
        }

        static List<ComponentEntity> toDao(List<Component> components) {
            return components.stream().map(Mapper::toDao).collect(Collectors.toList());
        }
    }
}
