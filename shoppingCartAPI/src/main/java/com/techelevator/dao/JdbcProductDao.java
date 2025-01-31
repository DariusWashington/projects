package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Component
public class JdbcProductDao implements ProductDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT product_id, product_sku, name, description, price, image_name FROM product ORDER BY product_id;";

        return jdbcTemplate.query(sql, new ProductRowMapper());
    }

    @Override
    public Product getProductById(int id) {
        Product product = null;
        String sql = "SELECT product_id, product_sku, name, description, price, image_name " +
                "FROM product WHERE product_id = ?";

        try {
            product = jdbcTemplate.queryForObject(sql, new ProductRowMapper(), id);
        } catch (RuntimeException e) {
            throw new DaoException("Unexpected error while selecting product by ID: " + id, e);
        }

        return product;
    }
    @Override
    public List<Product> getProductByOptionalSkuOrName(String sku, String name) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT product_id, product_sku, name, description, price, image_name " + //cast helped with data types problem
                "FROM product WHERE (CAST(? AS VARCHAR) IS NULL OR product_sku LIKE CAST(? AS VARCHAR)) " +
                "AND (CAST(? AS VARCHAR) IS NULL OR name LIKE CAST(? AS VARCHAR))";

        return jdbcTemplate.query(sql, new ProductRowMapper(), new Object[]{sku, "%" + sku + "%", name, "%" + name + "%"});
    }

        private static class ProductRowMapper implements RowMapper<Product> {

        @Override
        public Product mapRow(ResultSet rs, int i) throws SQLException {
            Product product = new Product();
            product.setProductId(rs.getInt("product_id"));
            product.setProductSku(rs.getString("product_sku"));
            product.setName(rs.getString("name"));
            product.setDescription(rs.getString("description"));
            product.setPrice(rs.getBigDecimal("price"));
            product.setImageName(rs.getString("image_name"));
            return product;
        }
    }
}



