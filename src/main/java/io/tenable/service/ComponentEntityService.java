package io.tenable.service;

import io.tenable.dto.Component;
import io.tenable.entity.ComponentEntity;
import io.tenable.metrics.Metrics;
import io.tenable.repository.ComponentRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ComponentEntityService {

    private ComponentRepository componentRepository;

    @Autowired
    public ComponentEntityService(ComponentRepository componentRepository) {
        this.componentRepository = componentRepository;
    }

    private static List<ComponentEntity> toDao(List<Component> components) {
        if (components == null || components.isEmpty()) {
            return null;
        }
        return components.stream().map(ComponentEntityService::toDao).collect(Collectors.toList());
    }

    private static ComponentEntity toDao(Component component) {
        ComponentEntity componentEntity = new ComponentEntity();
        componentEntity.setStatus(component.getStatus());
        componentEntity.setName(component.getName());
        componentEntity.setCompositeId(component.getCompositeId());

        return componentEntity;
    }

    private static Component toDto(ComponentEntity componentEntity) {
        Component component = new Component();
        component.setStatus(componentEntity.getStatus());
        component.setName(componentEntity.getName());
        component.setCompositeId(componentEntity.getCompositeId());

        return component;
    }

    /**
     * Assuming this to be an optional step so creating it as async and simply logging exceptions
     * can also be done via Publishing event to a queue
     *
     * @param components
     */
    @Async("threadPoolTaskExecutor")
    public void save(List<Component> components) {
        try {
            List<ComponentEntity> saved = componentRepository.saveAll(toDao(components));
            Metrics.add("DatabaseComponents", (long) saved.size(), null);
        } catch (Exception e) {
            log.error("Error saving components ", e);
        }
    }

    public List<Component> getAll() {
        return componentRepository.findAll().stream().map(ComponentEntityService::toDto).collect(Collectors.toList());
    }


}
