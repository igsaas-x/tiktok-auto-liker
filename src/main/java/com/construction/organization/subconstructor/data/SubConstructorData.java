package com.construction.organization.subconstructor.data;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
public class SubConstructorData {
    @NotNull
    private Long id;
    private String engFullName;
}
