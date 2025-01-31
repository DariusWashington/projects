package com.techelevator.model;

import org.springframework.lang.NonNull;

import javax.validation.constraints.Positive;
import java.util.Objects;

public class CartItem {


    private int cartItemId;
@NonNull
    private int userId;
@NonNull
    private int productId;
@Positive
    private int quantity;
    private Product product;


    public CartItem(){

    }
    public CartItem(int cartItemId,int userId, int productId, int quantity) {
        this.cartItemId = cartItemId;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    public int getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(int cartItemId) {
        this.cartItemId = cartItemId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    @Override
    public String toString() {
        return "CartItem{" +
                "userId=" + userId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartItem)) return false;
        CartItem cartItem = (CartItem) o;
        return getUserId() == cartItem.getUserId() && getProductId() == cartItem.getProductId() && getQuantity() == cartItem.getQuantity();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getProductId(), getQuantity());
    }
}

