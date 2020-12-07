package com.msa.jrg.dbfileservice.resources;

import com.msa.jrg.dbfileservice.model.DBFile;
import com.msa.jrg.dbfileservice.payload.UploadFileResponse;
import com.msa.jrg.dbfileservice.service.DBFileStorageService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/api/v1/dbfile")
public class DBFileRestController {

    @Qualifier("fileServicer")
    private final DBFileStorageService dbFileStorageService;

    public DBFileRestController(DBFileStorageService dbFileStorageService) {
        this.dbFileStorageService = dbFileStorageService;
    }

    //    @PreAuthorize("hasRole('USER')")
    @PostMapping("/uploadFile")
    public ResponseEntity<UploadFileResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        DBFile dbFile = dbFileStorageService.storeFile(file);

        URI fileDownLoadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(StringUtils.cleanPath(dbFile.getFileName()))
                .buildAndExpand(StringUtils.cleanPath(dbFile.getFileName()))
                .toUri();

        return ResponseEntity.created(fileDownLoadUri)
                .body(new UploadFileResponse(dbFile.getId(), dbFile.getFileName(), fileDownLoadUri.toString(),
                        file.getContentType(), file.getSize()));
    }

//    @PostMapping("/uploadMultipleFiles")
//    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
//        return Arrays.stream(files)
//                .map(this::uploadFile)
//                .collect(Collectors.toList());
//    }

    //    @PreAuthorize("hasRole('USER')")
    @GetMapping("/fileById/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
        DBFile dbFile = dbFileStorageService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename =\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getData()));
    }

    @GetMapping("/downloadFile/{fileName}")
    public ResponseEntity<Resource> findByFileName(@PathVariable String fileName) {
        DBFile dbFile = dbFileStorageService.findByFileName(fileName);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename =\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getData()));
    }

}