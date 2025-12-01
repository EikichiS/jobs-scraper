package com.eikichis.services;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

public class TigrisUploader {

    private static final String BUCKET = "jobs"; // tu bucket en Tigris

    public static void upload(String key, byte[] data) {
        S3Client s3 = TigrisClientFactory.create();

        PutObjectRequest req = PutObjectRequest.builder()
                .bucket(BUCKET)
                .key(key)
                .contentType("application/json")
                .build();

        s3.putObject(req, RequestBody.fromBytes(data));

        System.out.println("Uploaded to Tigris: " + key);
    }
}
