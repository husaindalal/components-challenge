package io.tenable.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
//@Builder TODO ideally use builder but Jackson requires NoArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Component implements Serializable {

    private String status; //can be a enum if there is logic associated with it. Or it can be a DB table

    private String name;

    private String compositeId;

}
