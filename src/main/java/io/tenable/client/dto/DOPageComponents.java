package io.tenable.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor

public class DOPageComponents implements Serializable {
    private final static long serialVersionUID = 7674980074927652183L;

    @JsonProperty("page")
    private DOPage page;
    @JsonProperty("components")
    private List<DOComponent> components = new ArrayList<>();


}
