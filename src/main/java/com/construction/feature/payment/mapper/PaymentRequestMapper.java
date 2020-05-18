package com.construction.feature.payment.mapper;

import com.construction.feature.payment.domain.PaymentRequest;
import com.construction.feature.payment.dto.PaymentRequestDTO;
import com.construction.persistence.mapper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentRequestMapper extends EntityMapper<PaymentRequestDTO, PaymentRequest> {
}