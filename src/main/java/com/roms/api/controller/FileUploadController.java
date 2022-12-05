package com.roms.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roms.api.dto.FileDto;
import com.roms.api.model.DigitalAssets;
import com.roms.api.service.DigitalAssetService;
import com.roms.api.service.MinioService;
import com.roms.api.utils.JwtTokenUtil;
import com.roms.api.utils.LoggedInUserDetails;
import io.minio.errors.*;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private LoggedInUserDetails loggedIn;

    @Autowired
    private DigitalAssetService digitalAssetService;

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
    public ResponseEntity<?> loadFile(
            @RequestParam(value ="fileName", defaultValue = "") String fileName,
            @RequestParam(value ="id", defaultValue = "") String bucketName,
    @RequestParam(value ="type", defaultValue = "") String type,
            @RequestParam(value ="key", defaultValue = "") String key) throws IOException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(type));
      /* if(!jwtTokenUtil.validateToken(key,loggedIn.getUser().getUsername())){
           return new ResponseEntity<>("not_found", HttpStatus.NOT_FOUND);
       }*/

        headers.add("Content-Disposition", "inline; filename=" + fileName);

        ResponseEntity<byte[]> returnValue = new ResponseEntity<>(IOUtils.toByteArray(minioService.getObject(fileName,bucketName)), headers, HttpStatus.OK);

        /*return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);*/
        return returnValue;

    }



    @GetMapping(value = "/migrate")
    public ResponseEntity<?> migrate()  {
        List<Map<String, Object>  > dataList = new ArrayList<>();

        List<DigitalAssets>   allFileList = digitalAssetService.findAll();
        allFileList.forEach(obj->{
            try {
                String newUrl =  minioService.getPreSignedUrl(obj.getFileName(),obj.getBucketName(),obj.getFileType());
                String oldurl= obj.getUrl();
                Map<String, Object> response = new HashMap();
                response.put("old",oldurl);
                response.put("id",obj.getId());
                response.put("new ",newUrl);

                dataList.add(response);
                obj.setUrl(newUrl);
                digitalAssetService.update(obj);
            } catch (ServerException e) {
                throw new RuntimeException(e);
            } catch (InsufficientDataException e) {
                throw new RuntimeException(e);
            } catch (ErrorResponseException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            } catch (InvalidKeyException e) {
                throw new RuntimeException(e);
            } catch (InvalidResponseException e) {
                throw new RuntimeException(e);
            } catch (XmlParserException e) {
                throw new RuntimeException(e);
            } catch (InternalException e) {
                throw new RuntimeException(e);
            }

        });
        return new ResponseEntity<>(dataList, HttpStatus.OK);

    }

}


