package com.ebc.ecard.util;

import software.amazon.awssdk.services.s3.model.Delete;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.Duration;

import javax.annotation.Resource;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectResponse;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.UploadPartRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

@Service
public class S3 {
	
	@Autowired
	S3Client s3Client;
	
	@Autowired
	S3Presigner s3Presigner;

	@Value("${cloud.aws.s3.bucket}")
    private String bucket;
    
    public String getFilePath(String fileName) {
    	GetObjectRequest request = GetObjectRequest.builder()
    			.bucket(bucket).key(fileName)
    			.build();
    	
    	GetObjectPresignRequest preRequest = GetObjectPresignRequest.builder()
    			.signatureDuration(Duration.ofMinutes(10))
    			.getObjectRequest(request)
    			.build();
    	
    	return s3Presigner.presignGetObject(preRequest).url().toExternalForm();
    }

    public String s32Upload(InputStream fileInputStream, long contentLength, String fileName) throws Exception {
		UploadPartRequest req = UploadPartRequest.builder().bucket(bucket).key(fileName).build();
		RequestBody reqBody = RequestBody.fromInputStream(fileInputStream, contentLength);
		s3Client.uploadPart(req, reqBody);

		GetUrlRequest url = GetUrlRequest.builder().bucket(bucket).key(fileName).build();
		return s3Client.utilities().getUrl(url).toExternalForm();
    }

    public boolean s3DeleteObject(String fileName) {
		DeleteObjectRequest deleteRequest = DeleteObjectRequest
			.builder()
			.bucket(bucket)
			.key(fileName)
			.build();


		DeleteObjectResponse response = s3Client.deleteObject(deleteRequest);

		return response.deleteMarker();
    }


}