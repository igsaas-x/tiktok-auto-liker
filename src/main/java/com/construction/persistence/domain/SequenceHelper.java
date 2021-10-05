package com.construction.persistence.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Table(name = "sequence_helper")
public class SequenceHelper {

    @Id
    @Column(name = "s_key")
    private String key;

    @Column(name = "s_value")
    private Long value;

    public void increase() {
        this.value += 1;
    }
}
