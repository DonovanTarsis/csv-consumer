package com.kintsugi.consumer.services;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.kintsugi.consumer.models.Product;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class CsvService {
    public static void delete(File file) {
        try {
            file.delete();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static List<Product> getProducts(String path) {
        try {
            Reader in = new FileReader(path);

            Iterable<CSVRecord> records = CSVFormat.DEFAULT.builder()
                    .setHeader()
                    .setDelimiter(";")
                    .build()
                    .parse(in);

            List<Product> listProducts = new ArrayList<>();
            for (CSVRecord record : records) {
                Product p = new Product();
                p.setNome(record.get(1).trim());
                p.setDescricao(record.get(2).trim());
                p.setQuantidade(Integer.parseInt(record.get(3).trim()));
                p.setData(record.get(4).trim());

                listProducts.add(p);

            }

            return listProducts;

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
}
