package com.ebc.ecard.util;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.ses.SesClient;

@Configuration
public class S3Config {
	@Value("${spring.profiles.active}")
	private String profileActive;
	
	@Bean
	public S3Client setS3Client() {
		S3Client s3Client = S3Client.builder()
				.credentialsProvider(DefaultCredentialsProvider.builder().build())
				.region(Region.AP_NORTHEAST_2)
				.build();
		
		return s3Client;
	}
	
	@Bean
	public S3Presigner setS3Presigner() {
		S3Presigner presigner = S3Presigner.builder()
				.credentialsProvider(DefaultCredentialsProvider.builder().build())
				.region(Region.AP_NORTHEAST_2)
				.build();
		
		return presigner;
	}
	
	@Bean
	public SesClient setSesClient() {
		SesClient sesClient = SesClient.builder()
				.credentialsProvider(DefaultCredentialsProvider.builder().build())
				.region(Region.AP_NORTHEAST_2)
				.build();
		
		return sesClient;
	}
	
	@Bean 
	public AmazonSimpleEmailService setSesTemplate() {
		AmazonSimpleEmailService sesTemplate = AmazonSimpleEmailServiceClientBuilder.standard()
				.withCredentials(new DefaultAWSCredentialsProviderChain())
				.withRegion(Regions.AP_NORTHEAST_2)
				.build();
		
		return sesTemplate;
	}
}
