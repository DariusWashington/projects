package com.techelevator.dao;

import com.techelevator.model.Product;

import java.util.List;

public interface ProductDao {
    List<Product> getAllProducts();
    Product getProductById(int id);
    List<Product> getProductByOptionalSkuOrName(String sku, String name);
}

