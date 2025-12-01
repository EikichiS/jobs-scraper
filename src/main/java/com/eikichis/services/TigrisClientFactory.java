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
                .endpointOverride(URI.create(System.getenv("TIGRIS_ENDPOINT")))
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(
                                        System.getenv("TIGRIS_ACCESS_KEY"),
                                        System.getenv("TIGRIS_SECRET_KEY")
                                )
                        )
                )
                .forcePathStyle(true)
                .build();
    }
}

