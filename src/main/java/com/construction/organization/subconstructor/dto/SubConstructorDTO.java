package com.construction.organization.subconstructor.dto;

import com.construction.feature.address.domain.Address;
import com.construction.organization.subconstructor.domain.Gender;
import com.construction.organization.subconstructor.domain.IDType;
import org.springframework.format.annotation.DateTimeFormat;

public class SubConstructorDTO {
    private String customerId;
    private String engFullName;
    private String khFullName;
    private String firstName;
    private String lastName;
    private Gender gender;
    private IDType idType;
    private String idNumber;
    private String mobile;
    private Address address;

    public SubConstructorDTO() {
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return this.customerId;
    }

    public void setEngFullName(String engFullName) {
        this.engFullName = engFullName;
    }

    public String getEngFullName() {
        return this.engFullName;
    }

    public void setKhFullName(String khFullName) {
        this.khFullName = khFullName;
    }

    public String getKhFullName() {
        return this.khFullName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Gender getGender() {
        return this.gender;
    }

    public void setIdType(IDType idType) {
        this.idType = idType;
    }

    public IDType getIdType() {
        return this.idType;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getIdNumber() {
        return this.idNumber;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return this.address;
    }
}