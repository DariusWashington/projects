package com.techelevator.controller;


import com.techelevator.dao.CartItemDao;
import com.techelevator.model.CartItem;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/cart")
@PreAuthorize("isAuthenticated()")
public class CartController {

    private final CartItemDao cartItemDao;

    @Autowired
    public CartController(CartItemDao cartItemDao) {
        this.cartItemDao = cartItemDao;

    }

    @PostMapping("/items")
    public void addCartItem(@RequestBody CartItem cartItem) {
        cartItemDao.addItemToCart(cartItem);
    }


    @GetMapping("/user/{userId}")
    public List<CartItem> getCartByUserId(@PathVariable int userId) {
        return cartItemDao.getCartByUserId(userId);
    }

    @DeleteMapping("/items/{itemId}")
    public void removeCartItem(@PathVariable int itemId) {
        cartItemDao.removeItemFromCart(itemId);
    }


    @DeleteMapping("/user/{userId}")
    public void clearCartByUserId(@PathVariable int userId) {
        cartItemDao.clearCart(userId);
    }

}