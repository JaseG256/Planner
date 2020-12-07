package com.msa.jrg.dbfileservice.service;

import com.msa.jrg.dbfileservice.config.DBFilePropertiesConfig;
import com.msa.jrg.dbfileservice.exception.FileStorageException;
import com.msa.jrg.dbfileservice.exception.MyFileNotFoundException;
import com.msa.jrg.dbfileservice.model.DBFile;
import com.msa.jrg.dbfileservice.repository.DBFileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Service(value = "fileServicer")
public class DBFileStorageServiceImpl implements DBFileStorageService {

    private final Logger logger = LoggerFactory.getLogger(DBFileStorageServiceImpl.class);

    private final DBFileRepository dbFileRepository;

    private final DBFilePropertiesConfig propertiesConfig;

    public DBFileStorageServiceImpl(DBFileRepository dbFileRepository, DBFilePropertiesConfig propertiesConfig) {
        this.dbFileRepository = dbFileRepository;
        this.propertiesConfig = propertiesConfig;
    }

    @Override
    public DBFile storeFile(MultipartFile file) {
        logger.info(propertiesConfig.getDbfile_storeFile_log_info(),
                StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename())));
        String fileName = Optional.of(StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename())))
                .orElse(propertiesConfig.getUnknown_name());

        try {
            if (fileName.contains("..")) {
                String msg = String.format(propertiesConfig.getInvalid_path_sequence(), fileName);
                logger.debug(msg);
                throw new FileStorageException(msg);
            }

            DBFile dbFile = new DBFile(fileName, file.getContentType(), file.getBytes());
            return dbFileRepository.save(dbFile);
        } catch (IOException e) {
            String msg = String.format(propertiesConfig.getFile_storage_exception(), fileName);
            logger.debug(msg);
            throw new FileStorageException(msg, e);
        }
    }

    @Override
    public DBFile getFile(String fileId) {
        logger.info(propertiesConfig.getDbfile_getFile_log_info(), fileId);
        return dbFileRepository.findById(fileId).
                orElseThrow(() -> {
                    String msg = String.format(propertiesConfig.getDbfile_exception_message(),  fileId);
                    logger.error(msg);
                    return new MyFileNotFoundException(msg);
                });
    }

    @Override
    public DBFile findByFileName(String fileName) {
        logger.info(propertiesConfig.getDbfile_findByFileName_log_info(), fileName);
        return dbFileRepository.findByFileName(fileName)
                .orElseThrow(() -> {
                    String msg = String.format(propertiesConfig.getDbfile_exception_message(), fileName);
                    logger.debug(msg);
                    return new MyFileNotFoundException(msg);
                });
    }
}
