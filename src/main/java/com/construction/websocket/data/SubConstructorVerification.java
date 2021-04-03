package com.construction.websocket.data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubConstructorVerification {
    private Long id;
    private boolean verified;
}
