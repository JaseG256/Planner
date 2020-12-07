package com.msa.jrg.userservice.model;

import lombok.Data;

@Data
public class DbFile {

    private String fileName;
    private byte[] data;

    public DbFile(String fileName, byte[] data) {
        this.fileName = fileName;
        this.data = data;
    }
}
