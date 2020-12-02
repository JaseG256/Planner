package com.msa.jrg.dbfileservice.exception;

import com.msa.jrg.core.model.MsaBaseException;

public class FileStorageException extends MsaBaseException {

    public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
