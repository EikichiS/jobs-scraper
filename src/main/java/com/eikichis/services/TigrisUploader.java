package com.eikichis.services;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

public class TigrisUploader {

    public static void upload(String key, byte[] data) {

        String bucket = System.getenv("BUCKET_NAME");
        if (bucket == null || bucket.isBlank()) {
            throw new IllegalStateException("Missing env var BUCKET_NAME");
        }
        S3Client s3 = TigrisClientFactory.create();

        PutObjectRequest req = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentType("application/json")
                .build();

        s3.putObject(req, RequestBody.fromBytes(data));

        System.out.println("Uploaded to Tigris: " + key);
    }
}


