package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.CartItem;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class JdbcCartItemDao implements CartItemDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcCartItemDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }

    @Override
    public List<CartItem> getCartByUserId(int userId) {
        String sql = "SELECT cart_item_id, user_id, product_id, quantity FROM cart_item WHERE user_id = ?";
        return jdbcTemplate.query(sql, cartItemMapper, userId);
    }

    @Override
    public void addItemToCart(CartItem cartItem) throws DaoException {
        String sql = "INSERT INTO cart_item (user_id, product_id, quantity) VALUES (?, ?, ?)";
        try {
            if (cartItem != null) {
                jdbcTemplate.update(sql, cartItem.getUserId(), cartItem.getProductId(), cartItem.getQuantity());
            } else {
                throw new DaoException("Cart item details cannot be null.");
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }
    public void removeItemFromCart(int itemId) throws DaoException {
        String sql = "DELETE FROM cart_item WHERE cart_item_id = ?";
        try {
            int rowsAffected = jdbcTemplate.update(sql, itemId);
            if (rowsAffected == 0) {
                throw new DaoException("No item found with ID: " + itemId);
            }
        } catch (DataAccessException e) {
            throw new DaoException("Error occurred while removing the item from the cart", e);
        }
    }

    @Override
    public void clearCart(int userId) {
        String sql = "DELETE FROM cart_item WHERE user_id = ?";
        jdbcTemplate.update(sql, userId);
    }


    private final RowMapper<CartItem> cartItemMapper = (rs, rowNum) -> {
        CartItem cartItem = new CartItem();
        cartItem.setCartItemId(rs.getInt("cart_item_id"));
        cartItem.setUserId(rs.getInt("user_id"));
        cartItem.setProductId(rs.getInt("product_id"));
        cartItem.setQuantity(rs.getInt("quantity"));
        return cartItem;
    };
}