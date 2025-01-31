package com.techelevator.dao;

import com.techelevator.model.CartItem;


import java.util.List;

public interface CartItemDao  {


     List<CartItem> getCartByUserId(int userId);

     void addItemToCart(CartItem cartItem);

     void removeItemFromCart(int itemId);

     void clearCart(int userId);

}



