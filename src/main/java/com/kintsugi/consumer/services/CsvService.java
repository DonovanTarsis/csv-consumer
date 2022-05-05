package com.kintsugi.consumer.services;

import java.io.File;

public class CsvService {
    public static void delete(File file) {
        try {
            file.delete();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
