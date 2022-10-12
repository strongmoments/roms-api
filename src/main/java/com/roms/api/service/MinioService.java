package com.roms.api.service;

import com.roms.api.controller.DepartmentController;
import com.roms.api.dto.FileDto;
import com.roms.api.model.DigitalAssets;
import com.roms.api.utils.LoggedInUserDetails;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;



@Service
public class MinioService {

    public static final Logger logger = LoggerFactory.getLogger(MinioService.class);

    @Autowired
    private MinioClient minioClient;
    @Autowired
    private LoggedInUserDetails loggedIn;

    @Autowired
    private DigitalAssetService digitalAssetService;

    @Value("${minio.env}")
    private String env;

    public InputStream getObject(String filename,String bucketName) {
        InputStream stream;
        try {
            stream = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(filename)
                    .build());
        } catch (Exception e) {
            logger.error("error when get list objects from minio: ", e);
            return null;
        }

        return stream;
    }

    public DigitalAssets uploadFile(FileDto request) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String bucketName = loggedIn.getUser().getEmployeId().getId();
        bucketName = env+"-"+bucketName;

        try {
            if(!doesBucketExist(bucketName)){
                createBuckets(bucketName);
            }
          minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(request.getFile().getOriginalFilename())
                    .stream(request.getFile().getInputStream(), request.getFile().getSize(), -1)
                    .build());

        } catch (Exception e) {
            logger.error("error when upload file minio : ", e);
        }
       String fileUrl = getPreSignedUrl(request.getFile().getOriginalFilename(),bucketName,request.getFile().getContentType());
        DigitalAssets digitalAsset = DigitalAssets.builder().url(fileUrl)
                .fileType(request.getFile().getContentType())
                .size(request.getFile().getSize())
                .bucketName(bucketName)
                .fileName(request.getFile().getOriginalFilename())
                .build();
        return digitalAssetService.save(digitalAsset);
    }
    private String getPreSignedUrl(String fileName,String bucketName,String type) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
     // return  new StringBuilder().append("/").append("v1/files").append("?").append("id=").append(bucketName).append("&").append("fileName=").append(fileName).append("&").append("type=").append(type).toString();
        return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().method(Method.GET).bucket(bucketName).object(fileName).build());

    }


    public List<Bucket> getAllBuckets() {
        try {
            return minioClient.listBuckets();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public boolean doesBucketExist(String bucketName) {
        try {
            return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).region(bucketName).build());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public void  createBuckets(String bucketName) {
        try {

         minioClient.makeBucket( MakeBucketArgs.builder().bucket(bucketName).region(bucketName).build());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
