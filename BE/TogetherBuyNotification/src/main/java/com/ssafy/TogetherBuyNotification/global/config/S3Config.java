package com.ssafy.TogetherBuyNotification.global.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class S3Config {
    //    @Value("${cloud.aws.credentials.access-key}") // application.yml 에 명시한 내용
    private String accessKey = "sdfsadfsdf";

    //    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey = "flaksdjclksandlck";

    //    @Value("${cloud.aws.region.static}")
    private String region = "sdklfjslakdjclskadjc";

    @Bean
    public AmazonS3Client amazonS3Client() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);
        return (AmazonS3Client) AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();
    }
}
