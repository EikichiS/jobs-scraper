package com.eikichis.services;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

public class TigrisClientFactory {
    public static S3Client create() {
        return S3Client.builder()
                .region(Region.of(System.getenv("AWS_REGION")))
                .endpointOverride(URI.create(System.getenv("AWS_ENDPOINT_URL_S3")))
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(
                                        System.getenv("AWS_ACCESS_KEY_ID"),
                                        System.getenv("AWS_SECRET_ACCESS_KEY")
                                )
                        )
                )
                .forcePathStyle(true)
                .build();
    }
}


