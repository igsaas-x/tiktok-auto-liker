package com.construction.organization.subconstructor.repository;

import com.construction.organization.subconstructor.data.SubConstructorData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubConstructorDataRepository extends JpaRepository<SubConstructorData, Long> {

    List<SubConstructorData> findByBase64(String base64);
}
