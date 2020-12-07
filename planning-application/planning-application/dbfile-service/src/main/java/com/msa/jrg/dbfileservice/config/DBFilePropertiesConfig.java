package com.msa.jrg.dbfileservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "dbfile")
public class DBFilePropertiesConfig {

    private String dbfile_delete_apiResponse_message, dbfile_resource_name, dbfile_field_name, dbfile_field_id, dbfile_field_email,
            dbfile_exception_message, file_storage_exception, invalid_path_sequence, unknown_name,
            dbfile_getFile_log_info, dbfile_findByFileName_log_info, dbfile_storeFile_log_info;
}
