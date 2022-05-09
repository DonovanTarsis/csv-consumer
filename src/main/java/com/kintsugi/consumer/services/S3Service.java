package com.kintsugi.consumer.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kintsugi.consumer.models.Product;
import com.kintsugi.consumer.utils.ClientS3;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsResponse;
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

    public static String getObject(String objKey) {
        try {

            GetObjectRequest request = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objKey)
                    .build();

            ResponseBytes<GetObjectResponse> objBytes = client.getObjectAsBytes(request);

            byte[] data = objBytes.asByteArray();

            String path = objKey.replace(".", "-" + new Date().getTime() + ".");
            File myfile = new File(path);
            OutputStream os = new FileOutputStream(myfile);
            os.write(data);

            os.close();
            return path;
        } catch (S3Exception e) {
            System.err.println("Status code => " + e.statusCode());
            System.err.println(e.awsErrorDetails().errorMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    // public static void putObject() {
    // // PutObjectRequest request = PutObjectRequest.builder()
    // // .bucket(bucketName)
    // // .key("teste.txt")
    // // .build();

    // // client.putObject(request, RequestBody.fromFile(new File("teste.txt")));
    // }
}
