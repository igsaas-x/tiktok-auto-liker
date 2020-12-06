package com.construction.feature.address.service;

import com.construction.feature.address.domain.Address;
import com.construction.feature.address.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private AddressRepository repository;

    public List<Address> getChild(final Long id) {
        return repository.findAllByParentId(id);
    }
}
