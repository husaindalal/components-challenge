package io.tenable.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "person")

@Data
//@Builder TODO ideally use builder but Jackson requires NoArgsConstructor
@NoArgsConstructor
public class ComponentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    @NotNull
    private String lastName;
}
