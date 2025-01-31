package com.techelevator.controller;

import com.techelevator.dao.WishlistDao;

import com.techelevator.model.Wishlist;
import com.techelevator.model.WishlistItem;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/wishlists")
public class WishlistController {

    private final WishlistDao wishlistDao;

    public WishlistController(WishlistDao wishlistDao) {
        this.wishlistDao = wishlistDao;
    }


    @RequestMapping(path = "/{wishlistId}/user/{userId}", method = RequestMethod.GET)
    public Wishlist getWishlistById(@PathVariable int wishlistId, @PathVariable int userId) {
        Wishlist wishlist = wishlistDao.getWishlistByWishlistIdAndUserId(wishlistId, userId);
        if (wishlist == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wishlist not found");
        }
        return wishlist;
    }


    @RequestMapping(path = "/user/{userId}", method = RequestMethod.GET)
    public List<Wishlist> getWishlistsByUserId(@PathVariable int userId) {
        return wishlistDao.getWishlists(userId);
    }


    @RequestMapping(path = "", method = RequestMethod.POST)
    public Wishlist createWishlist(@RequestBody Wishlist wishlist) {
        return wishlistDao.createWishlist(wishlist);
    }


    @RequestMapping(path = "/items", method = RequestMethod.POST)
    public WishlistItem addWishlistItem(@RequestBody WishlistItem wishlistItem) {
        return wishlistDao.createWishlistItem(wishlistItem);
    }


    @RequestMapping(path = "/{wishlistId}/user/{userId}", method = RequestMethod.DELETE)
    public void deleteWishlistById(@PathVariable int userId, @PathVariable int wishlistId) {
        wishlistDao.deleteWishlistById(userId, wishlistId);
    }


    @RequestMapping(path = "/{wishlistId}/items/{productId}", method = RequestMethod.DELETE)
    public void deleteWishlistItem(@PathVariable int wishlistId, @PathVariable int productId, @PathVariable int userId) {
        wishlistDao.deleteWishlistItem(userId, wishlistId, productId);
    }

    @RequestMapping(path = "/{wishlistId}/items/{productId}", method = RequestMethod.GET)
    public WishlistItem getWishlistItemByWishlistIdAndProductId(@PathVariable int wishlistId, @PathVariable int productId) {
        WishlistItem wishlistItem = wishlistDao.getWishlistItemByWishlistIdAndProductId(wishlistId, productId);
        if (wishlistItem == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wishlist item not found");
        }
        return wishlistItem;
    }

    @RequestMapping(path = "/items/{wishlistItemId}", method = RequestMethod.GET)
    public WishlistItem getWishlistItemById(@PathVariable int wishlistItemId) {
        WishlistItem wishlistItem = wishlistDao.getWishlistItemById(wishlistItemId);
        if (wishlistItem == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wishlist item not found");
        }
        return wishlistItem;
    }
}