package com.msa.jrg.dbfileservice.payload;

import lombok.Data;

@Data
public class UploadFileResponse {

    private String id;
    private String fileName;
    private String uploadFileUri;
    private String fileType;
    private long size;

    public UploadFileResponse(String id, String fileName, String uploadFileUri, String fileType, long size) {
        this.fileName = fileName;
        this.uploadFileUri = uploadFileUri;
        this.fileType = fileType;
        this.size = size;
    }

}
