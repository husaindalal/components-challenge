package io.tenable.service;

import io.tenable.dto.Component;
import io.tenable.entity.ComponentEntity;
import io.tenable.exception.CustomException;
import io.tenable.repository.ComponentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ComponentService {

    private ComponentRepository componentRepository;

    @Autowired
    public ComponentService(ComponentRepository componentRepository) {
        this.componentRepository = componentRepository;
    }

    public List<Component> findAll() {
        return Mapper.toDto(componentRepository.findAll());
    }

    public Optional<Component> findById(Long id) {
        if (id == null || id < 1) {
            return Optional.empty();
        }
        return componentRepository.findById(id).map(Mapper::toDto);
    }

    public Component save(@Valid Component component) {
        try {
            return Mapper.toDto(componentRepository.save(Mapper.toDao(component)));
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), e);
        }
    }

    public void delete(Long personId) {
        Optional<ComponentEntity> person = componentRepository.findById(personId);
        if (person.isPresent()) {
            componentRepository.deleteById(personId);

        } else {
            throw new CustomException("Component not found");
        }

    }

    static class Mapper {
        static Component toDto(ComponentEntity componentEntity) {
            Component component = new Component();
            component.setId(componentEntity.getId());
            component.setFirstName(componentEntity.getFirstName());
            component.setLastName(componentEntity.getLastName());

            return component;
        }

        static List<Component> toDto(List<ComponentEntity> personEntities) {
            return personEntities.stream().map(Mapper::toDto).collect(Collectors.toList());
        }

        static ComponentEntity toDao(Component component) {
            ComponentEntity componentEntity = new ComponentEntity();
            componentEntity.setId(component.getId());
            componentEntity.setFirstName(component.getFirstName());
            componentEntity.setLastName(component.getLastName());

            return componentEntity;
        }

        static List<ComponentEntity> toDao(List<Component> components) {
            return components.stream().map(Mapper::toDao).collect(Collectors.toList());
        }
    }
}
