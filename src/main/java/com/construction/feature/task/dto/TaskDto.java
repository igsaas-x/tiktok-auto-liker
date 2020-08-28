package com.construction.feature.task.dto;

import com.construction.organization.subconstructor.data.SubConstructorDto;
import com.construction.persistence.domain.ObjectStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(value = {
        "id", "status",
        "createdBy", "updatedBy",
        "verifiedBy", "approvedBy",
        "subConstructors"}, allowGetters = true)
public class TaskDto {
    private Long id;
    @JsonProperty("name")
    private String taskTemplateName;
    private Integer quantity;
    private String unit;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private BigDecimal actualPrice;
    @NotNull
    private Long taskTemplateId;
    @JsonProperty("createdBy")
    private String createdByUserName;
    @JsonProperty("updatedBy")
    private String updatedByUserName;
    @JsonProperty("verifiedBy")
    private String verifiedByUserName;
    @JsonProperty("approvedBy")
    private String approvedByUserName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime verifiedAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime approvedAt;
    private ObjectStatus status;
    private List<SubConstructorDto> subConstructors;
}
