package com.msa.jrg.dbfileservice.service;

import com.msa.jrg.dbfileservice.model.DBFile;
import org.springframework.web.multipart.MultipartFile;

public interface DBFileStorageService {

    DBFile storeFile(MultipartFile file);

    DBFile getFile(String fileId);

    DBFile findByFileName(String fileName);
}
