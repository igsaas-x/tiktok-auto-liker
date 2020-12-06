package com.construction.feature.address.controller;

import com.construction.feature.address.domain.Address;
import com.construction.feature.address.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    @GetMapping("/province")
    public List<Address> getProvince() {
        return addressService.getChild(1L);
    }

    @GetMapping("/{id}")
    public List<Address> getByParentId(@PathVariable final Long id) {
        return addressService.getChild(id);
    }

    @GetMapping("/search")
    public List<Address> getByParentName(@RequestParam final String name) {
        return addressService.getChild(name);
    }
}
