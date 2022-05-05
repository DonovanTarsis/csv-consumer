package com.kintsugi.consumer.utils;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

public class ClientS3 {
    public static S3Client getClient() {
        return S3Client.builder().region(Region.US_EAST_1).build();
    }
}
