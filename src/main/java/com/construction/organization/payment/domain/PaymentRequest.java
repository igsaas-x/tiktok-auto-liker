package com.construction.organization.payment.domain;

import com.construction.feature.task.domain.Task;
import com.construction.organization.subconstructor.domain.SubConstructor;
import com.construction.persistence.domain.SimpleAuditingEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Filter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Filter(name = "myObjectFilter", condition = "created_by = :id")
@Filter(name = "readableObjectFilter", condition = "created_by = :id")
public class PaymentRequest extends SimpleAuditingEntity {

    @ManyToOne
    @JoinColumn
    Task task;

    String description;

    @ManyToOne
    @JoinColumn
    SubConstructor subConstructor;

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "done", column = @Column(name = "is_submitted")),
            @AttributeOverride(name = "doneAt", column = @Column(name = "submitted_at"))})
    @AssociationOverride(name = "doneBy", joinColumns = @JoinColumn(name = "submitted_by"))
    RequestStatus submitted;

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "done", column = @Column(name = "is_verified")),
            @AttributeOverride(name = "doneAt", column = @Column(name = "verified_at"))})
    @AssociationOverride(name = "doneBy", joinColumns = @JoinColumn(name = "verified_by"))
    RequestStatus verified;

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "done", column = @Column(name = "is_confirmed")),
            @AttributeOverride(name = "doneAt", column = @Column(name = "confirmed_at"))})
    @AssociationOverride(name = "doneBy", joinColumns = @JoinColumn(name = "confirmed_by"))
    RequestStatus confirmed;

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "done", column = @Column(name = "is_reviewed")),
            @AttributeOverride(name = "doneAt", column = @Column(name = "reviewed_at"))})
    @AssociationOverride(name = "doneBy", joinColumns = @JoinColumn(name = "reviewed_by"))
    RequestStatus reviewed;

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "done", column = @Column(name = "is_approved")),
            @AttributeOverride(name = "doneAt", column = @Column(name = "approved_at"))})
    @AssociationOverride(name = "doneBy", joinColumns = @JoinColumn(name = "approved_by"))
    RequestStatus approved;

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "done", column = @Column(name = "is_paid")),
            @AttributeOverride(name = "doneAt", column = @Column(name = "paid_at"))})
    @AssociationOverride(name = "doneBy", joinColumns = @JoinColumn(name = "paid_by"))
    RequestStatus paid;

}
