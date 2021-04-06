package com.construction.basic.file.repository;

import com.construction.basic.file.domain.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<FileEntity, Long> {

    void deleteByName(final String name);

    Optional<FileEntity> findByName(String name);
}
