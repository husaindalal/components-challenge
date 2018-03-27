package io.tenable.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
//@Builder TODO ideally use builder but Jackson requires NoArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Component implements Serializable {
    @Min(1)
    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

}
