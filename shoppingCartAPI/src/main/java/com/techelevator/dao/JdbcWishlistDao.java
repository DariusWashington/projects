package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Wishlist;
import com.techelevator.model.WishlistItem;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;
@Component
public class JdbcWishlistDao implements WishlistDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcWishlistDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Wishlist> getWishlists(int userId) {
        List<Wishlist> list = new ArrayList<>();
        String sql = "SELECT * FROM wishlist WHERE user_id = ? ORDER BY wishlist_id";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            while (results.next()) {
                Wishlist wishlist = mapRowToWishlist(results);
                list.add(wishlist);
            }
        } catch (Exception e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return list;

    }
        @Override
    public Wishlist getWishlistByWishlistIdAndUserId(int userId, int wishlistId) {
        Wishlist wishlist = null;
        String sql = "SELECT * FROM wishlist WHERE user_id = ? AND wishlist_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, wishlistId);
            if (results.next()) {
                wishlist = mapRowToWishlist(results);
            }
        } catch (Exception e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return wishlist;
    }


    @Override
    public Wishlist createWishlist(Wishlist wishlist) {
        String sql = "INSERT INTO wishlist (user_id, name) VALUES (?, ?) RETURNING wishlist_id";
        try {
            Integer wishlistId = jdbcTemplate.queryForObject(sql, Integer.class, wishlist.getUserId(), wishlist.getName());
            wishlist.setWishlistId(wishlistId);
        } catch (Exception e) {
            throw new DaoException("Unable to create wishlist", e);
        }
        return wishlist;
    }

    @Override
    public int deleteWishlistById(int userId, int wishlistId) {
        String sql = "DELETE FROM wishlist WHERE wishlist_id = ? AND user_id = ?";
        try {
            return jdbcTemplate.update(sql, wishlistId, userId);
        } catch (Exception e) {
            throw new DaoException("Unable to delete wishlist", e);
        }
    }
    @Override
    public WishlistItem createWishlistItem(WishlistItem wishlistItem) {
        String sql = "INSERT INTO wishlist_item (wishlist_id, product_id) VALUES (?, ?) RETURNING wishlist_item_id";
        try {
            Integer newId = jdbcTemplate.queryForObject(sql, Integer.class, wishlistItem.getWishlistId(), wishlistItem.getProductId());
            wishlistItem.setWishlistItemId(newId);
        } catch (Exception e) {
            throw new DaoException("Unable to add item to wishlist", e);
        }
        return wishlistItem;
    }
    @Override
    public int deleteWishlistItem(int userId, int wishlistId, int productId) {
        String sql = "DELETE FROM wishlist_item WHERE wishlist_id = ? AND product_id = ?";
        try {
            return jdbcTemplate.update(sql, wishlistId, productId);
        } catch (Exception e) {
            throw new DaoException("Unable to remove item from wishlist", e);
        }
    }

    @Override
    public WishlistItem getWishlistItemByWishlistIdAndProductId(int wishlistId, int productId) {
        WishlistItem wishlistItem = null;
        String sql = "SELECT * FROM wishlist_item WHERE wishlist_id = ? AND product_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, wishlistId, productId);
            if (results.next()) {
                wishlistItem = mapRowToWishlistItem(results);
            }
        } catch (Exception e) {
            throw new DaoException("Unable to retrieve wishlist item", e);
        }
        return wishlistItem;
    }
    @Override
    public WishlistItem getWishlistItemById(int wishlistItemId) {
        WishlistItem wishlistItem = null;
        String sql = "SELECT * FROM wishlist_item WHERE wishlist_item_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, wishlistItemId);
            if (results.next()) {
                wishlistItem = mapRowToWishlistItem(results);
            }
        } catch (Exception e) {
            throw new DaoException("Unable to retrieve wishlist item", e);
        }
        return wishlistItem;
    }
    private Wishlist mapRowToWishlist(SqlRowSet rs) {
        Wishlist wishlist = new Wishlist();
        wishlist.setWishlistId(rs.getInt("wishlist_id"));
        wishlist.setUserId(rs.getInt("user_id"));
        wishlist.setName(rs.getString("name"));
        return wishlist;
    }

    private WishlistItem mapRowToWishlistItem(SqlRowSet rs) {
        WishlistItem wishlistItem = new WishlistItem();
        wishlistItem.setWishlistItemId(rs.getInt("wishlist_item_id"));
        wishlistItem.setWishlistId(rs.getInt("wishlist_id"));
        wishlistItem.setProductId(rs.getInt("product_id"));
        return wishlistItem;
    }
}