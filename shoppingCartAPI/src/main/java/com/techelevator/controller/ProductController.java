package com.techelevator.controller;

import com.techelevator.dao.ProductDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.Product;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/products")
@PreAuthorize("permitAll")

public class ProductController {

    private ProductDao productDao;

    public ProductController(ProductDao productDao) {
        this.productDao = productDao;
    }


    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Product getProductById(@PathVariable int id) {
        Product product = productDao.getProductById(id);
        if (product == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
        return product;
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<Product> getProductByOptionalSkuOrName(@RequestParam(value = "product_sku", required = false) String sku,
                                                       @RequestParam(value = "product_name", required = false) String name) {
        try {
            if (sku == null && name == null) {
                return productDao.getAllProducts();
            } else {

                return productDao.getProductByOptionalSkuOrName(sku, name);
            }
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "DAO error - " + e.getMessage());
        }
    }
}
