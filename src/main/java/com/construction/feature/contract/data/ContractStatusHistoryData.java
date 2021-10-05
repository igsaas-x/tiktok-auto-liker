package com.construction.feature.contract.data;

import com.construction.organization.payment.domain.CommandType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContractStatusHistoryData {

    CommandType commandType;

    String attachment;

    String comment;

    BigDecimal originApprovedAmount;

    BigDecimal newApprovedAmount;
}
