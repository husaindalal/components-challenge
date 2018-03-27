package io.tenable.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "person")

@Data
//@Builder TODO ideally use builder but Jackson requires NoArgsConstructor
@NoArgsConstructor
public class ComponentEntity {

    @Id
    private String compositeId;

    private String name;

    private String status;
}
