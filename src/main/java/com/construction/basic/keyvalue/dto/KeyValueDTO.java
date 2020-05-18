package com.construction.basic.keyvalue.dto;

import com.construction.basic.keyvalue.domain.KeyValueGroup;
import lombok.Data;

import java.util.Map;

@Data
public class KeyValueDTO {
    private KeyValueGroup group;
    private String key;
    private String value;
    private Map<String, Object> jsonValue;
    private String remarks;
    private boolean removable;
}