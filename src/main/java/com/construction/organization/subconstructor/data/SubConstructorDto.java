package com.construction.organization.subconstructor.data;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
public class SubConstructorDto {
    @NotNull
    private Long id;
    private String engFullName;
}
