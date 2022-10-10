package com.roms.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roms.api.dto.FileDto;
import com.roms.api.model.DigitalAssets;
import com.roms.api.service.MinioService;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/v1/files")
public class FileUploadController {

    @Autowired
    private  MinioService minioService;


    @RequestMapping(value = "/upload" , method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE  })
    public ResponseEntity<Map<String,Object>> uploadFile(@RequestParam(value="files") MultipartFile[] filse) throws IOException {

        Map<String, Object> response = new HashMap();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, MultipartFile> imageData = new HashMap();
        try {
            List<DigitalAssets> digitalAsset = new ArrayList<>();
             for(MultipartFile file : filse){
                 FileDto fileDto = FileDto.builder()
                         .file(file).build();
                 digitalAsset.add(minioService.uploadFile(fileDto));
            }
            response.put("status","success");
            response.put("data",digitalAsset);
        } catch(Exception ee){
            ee.printStackTrace();
            response.put("status","error");
            response.put("error", ee.getMessage());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "")
    public ResponseEntity<?> loadApliedLeaveByLeaveStatus(
            @RequestParam(value ="fileName", defaultValue = "") String fileName,
            @RequestParam(value ="id", defaultValue = "") String bucketName) throws IOException {

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(IOUtils.toByteArray(minioService.getObject(fileName,bucketName)));

    }

}


