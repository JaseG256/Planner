package com.msa.jrg.userservice.client;

import com.msa.jrg.userservice.config.WebClientPropertiesConfig;
import com.msa.jrg.userservice.payload.UploadFileResponse;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;

@Service("dbFileRestClient")
public class DBFileRestClient {

    private final RestTemplate restTemplate;
    private final WebClientPropertiesConfig propertiesConfig;

    public DBFileRestClient(@LoadBalanced RestTemplate restTemplate, WebClientPropertiesConfig propertiesConfig) {
        this.restTemplate = restTemplate;
        this.propertiesConfig = propertiesConfig;
    }

    public ResponseEntity<UploadFileResponse> uploadImage(MultipartFile file) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body =
                new LinkedMultiValueMap<>();
        body.add(propertiesConfig.getFileType(), file);
        HttpEntity<MultiValueMap<String, Object>> requestEntitiy =
                new HttpEntity<>(body, headers);
        return restTemplate.postForEntity(propertiesConfig.getBaseUrl()+
                propertiesConfig.getUploadFileUrl(), requestEntitiy, UploadFileResponse.class);
    }

    public ResponseEntity<Resource> findByFileName(String fileName) {
        return restTemplate.getForEntity("http://DBFILE-SERVICE/api/v1/dbfile/downloadFile/" + fileName,
                Resource.class);
    }
}
