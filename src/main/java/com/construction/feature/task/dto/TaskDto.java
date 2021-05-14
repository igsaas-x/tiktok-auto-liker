package com.construction.feature.task.dto;

import com.construction.feature.task.domain.ContractType;
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
        "id",
        "status",
        "createdBy",
        "updatedBy",
        "verifiedBy",
        "approvedBy",
        "subConstructors"}, allowGetters = true)
public class TaskDto {
    private Long id;
    @JsonProperty("name")
    private String taskTemplateName;
    @NotNull
    private Integer quantity;
    private ContractType contractType;
    private String unit;
    @NotNull
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    @NotNull
    private Long taskTemplateId;
    @JsonProperty(value = "createdBy", access = JsonProperty.Access.READ_ONLY)
    private String createdByUserName;
    @JsonProperty(value = "updatedBy", access = JsonProperty.Access.READ_ONLY)
    private String updatedByUserName;
    @JsonProperty(value = "verifiedBy", access = JsonProperty.Access.READ_ONLY)
    private String verifiedByUserName;
    @JsonProperty(value = "approvedBy", access = JsonProperty.Access.READ_ONLY)
    private String approvedByUserName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime verifiedAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime approvedAt;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private ObjectStatus status;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigDecimal paidAmount;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigDecimal availableAmount;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Float availableAmountAsPercent;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BOQData boq;
    private List<SubConstructorDto> subConstructors;
}
