package com.techelevator.dao;

import com.techelevator.model.Wishlist;
import com.techelevator.model.WishlistItem;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.List;

public interface WishlistDao {

    List<Wishlist> getWishlists(int userId);

    Wishlist getWishlistByWishlistIdAndUserId(int userId, int id);

    Wishlist createWishlist(Wishlist wishlist);

    int deleteWishlistById(int userId, int wishlistId);

    WishlistItem createWishlistItem(WishlistItem wishlistItem);

    int deleteWishlistItem(int userId, int wishlistId, int productId);

    WishlistItem getWishlistItemByWishlistIdAndProductId(int wishlistId, int productId);

    WishlistItem getWishlistItemById(int wishlistItemId);

    private Wishlist mapRowToWishlist(SqlRowSet rs) {
        return null;
    }
}
