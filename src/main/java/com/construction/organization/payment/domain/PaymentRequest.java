package com.construction.organization.payment.domain;

import com.construction.feature.house.domain.House;
import com.construction.feature.project.domain.Project;
import com.construction.feature.street.domain.Street;
import com.construction.organization.subconstructor.domain.SubConstructor;
import com.construction.persistence.converter.StringListConverter;
import com.construction.persistence.domain.SimpleAuditingEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Filter(name = "myObjectFilter", condition = "created_by = :id")
@Filter(name = "readableObjectFilter", condition = "created_by = :id")
@FilterDef(name = "pendingRequestFilter", parameters = @ParamDef(name = "status", type = "string"))
public class PaymentRequest extends SimpleAuditingEntity {

    @Column(unique = true)
    String externalId;

    String invoiceNumber;

    @ManyToOne
    @JoinColumn(updatable = false)
    SubConstructor subConstructor;

    LocalDate requestDate;

    Long projectId;

    Long streetId;

    Long houseId;

    @Convert(converter = StringListConverter.class)
    @Column(columnDefinition = "text")
    List<String> attachments;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "payment_request_id")
    @Filter(name = "pendingRequestFilter", condition = "status = :status")
    List<PaymentEntry> entries;
}
