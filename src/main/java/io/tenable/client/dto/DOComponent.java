package io.tenable.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor

public class DOComponent implements Serializable {
    private final static long serialVersionUID = 4407549116749457004L;

    @JsonProperty("id")
    private String id;

    @JsonProperty("status")
    private String status;
    @JsonProperty("name")
    private String name;
    @JsonProperty("page_id")
    private String pageId;
    @JsonProperty("group_id")
    private String groupId;

    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("updated_at")
    private String updatedAt;
    @JsonProperty("position")
    private Long position;
    @JsonProperty("description")
    private Object description;
    @JsonProperty("showcase")
    private Boolean showcase;

}