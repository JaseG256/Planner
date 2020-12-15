package com.msa.jrg.userservice.client;

import com.msa.jrg.userservice.config.WebClientPropertiesConfig;
import com.msa.jrg.userservice.payload.UploadFileResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;

@Service("dbFileRestClient")
public class DBFileRestClient {

    private final Logger logger = LoggerFactory.getLogger(DBFileRestClient.class);
    private final RestTemplate restTemplate;
    private final DiscoveryService discoveryService;
    private final WebClientPropertiesConfig propertiesConfig;

    public DBFileRestClient(
            @LoadBalanced RestTemplate restTemplate,
            DiscoveryService discoveryService,
            WebClientPropertiesConfig propertiesConfig) {
        this.restTemplate = restTemplate;
        this.discoveryService = discoveryService;
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

    public ResponseEntity<?> findByFileName(String fileName) {
        logger.trace("FileName: {}", fileName);
        String serviceAddress = discoveryService.getServiceAddressFor("DBFILE-SERVICE");
        logger.trace("Service address: {}", serviceAddress);
        System.out.print(String.format("Service address: %s", serviceAddress));
        return restTemplate.getForEntity(
                serviceAddress + "api/v1/dbfile/downloadFile/" + fileName,
                Resource.class);
    }
}
