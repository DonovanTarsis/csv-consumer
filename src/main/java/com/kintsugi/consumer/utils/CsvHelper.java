package com.kintsugi.consumer.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.kintsugi.consumer.models.Product;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class CsvHelper {
  public static String TYPE = "text/csv";
  static String[] HEADERs = { "id", "nome", "descricao", "quantidade", "data" };

  public static List<Product> csvToTutorials(InputStream is) {
    try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        CSVParser csvParser = new CSVParser(fileReader,
            CSVFormat.DEFAULT)) {

      List<Product> productList = new ArrayList<>();

      Iterable<CSVRecord> csvRecords = csvParser.getRecords();

      for (CSVRecord csvRecord : csvRecords) {
        Product p = new Product();

        p.setNome(csvRecord.get("nome"));
        p.setDescricao(csvRecord.get("descricao"));
        p.setQuantidade(Integer.parseInt(csvRecord.get("quantidade")));
        p.setData(LocalDate.parse(csvRecord.get("data")));

        productList.add(p);
      }

      return productList;
    } catch (IOException e) {
      throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
    }
  }

}
