package com.construction.feature.payment.dto;

import com.construction.feature.task.domain.Task;
import com.construction.user.authentication.domain.AppUser;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class PaymentRequestDTO {
    private AppUser reviewedBy;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime reviewedAt;
    private Task task;
    private String description;
    private AppUser receiver;
}