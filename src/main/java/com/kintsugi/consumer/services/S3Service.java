package com.kintsugi.consumer.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;

import com.kintsugi.consumer.utils.ClientS3;

import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsResponse;
import software.amazon.awssdk.services.s3.model.PutObjectAclResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.S3Object;

public class S3Service {

    private static final S3Client client = ClientS3.getClient();
    private static final String bucketName = "bucket-group-7";

    public static void listObjects() {
        try {
            ListObjectsRequest request = ListObjectsRequest.builder().bucket(bucketName).build();

            ListObjectsResponse result = client.listObjects(request);

            for (S3Object a : result.contents()) {
                System.out.println(a.key());
            }

            client.close();
        } catch (S3Exception e) {
            System.err.println("Status code => " + e.statusCode());
            System.err.println(e.awsErrorDetails().errorMessage());
        }
    }

    public static void getObject(String objKey) {
        try {

            GetObjectRequest request = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objKey)
                    .build();

            ResponseBytes<GetObjectResponse> objBytes = client.getObjectAsBytes(request);

            byte[] data = objBytes.asByteArray();

            File myfile = new File(objKey);
            OutputStream os = new FileOutputStream(myfile);
            os.write(data);

            os.close();
            client.close();

        } catch (S3Exception e) {
            System.err.println("Status code => " + e.statusCode());
            System.err.println(e.awsErrorDetails().errorMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    // public static void putObject() {
    // // PutObjectRequest request = PutObjectRequest.builder()
    // // .bucket(bucketName)
    // // .key("teste.txt")
    // // .build();

    // // client.putObject(request, RequestBody.fromFile(new File("teste.txt")));
    // }
}
