package com.msa.jrg.dbfileservice.repository;

import com.msa.jrg.dbfileservice.model.DBFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DBFileRepository extends JpaRepository<DBFile, String> {

    Optional<DBFile> findByFileName(String fileName);
}
