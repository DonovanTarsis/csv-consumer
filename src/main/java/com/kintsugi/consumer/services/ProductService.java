package com.kintsugi.consumer.services;

import java.util.List;

import com.kintsugi.consumer.models.Product;
import com.kintsugi.consumer.respositories.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private static ProductRepository productRepository;

    @Autowired
    public void injectRepo(ProductRepository pr) {
        ProductService.productRepository = pr;
    }

    public static void saveAll(List<Product> list) {
        productRepository.saveAll(list);
    }
}
