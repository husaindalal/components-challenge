package io.tenable.repository;

import io.tenable.entity.ComponentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComponentRepository extends JpaRepository<ComponentEntity, Long> {

    List<ComponentEntity> findByLastName(@Param("name") String name);

}