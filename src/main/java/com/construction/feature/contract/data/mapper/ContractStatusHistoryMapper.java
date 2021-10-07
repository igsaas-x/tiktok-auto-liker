package com.construction.feature.contract.data.mapper;

import com.construction.feature.contract.data.ContractStatusHistoryData;
import com.construction.feature.contract.domain.ContractStatusHistory;
import com.construction.persistence.mapper.DtoMapper;
import org.springframework.stereotype.Component;

@Component
public class ContractStatusHistoryMapper extends DtoMapper<ContractStatusHistory, ContractStatusHistoryData> {
}
