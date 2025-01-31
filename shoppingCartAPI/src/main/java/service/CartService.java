package service;

import com.techelevator.dao.CartItemDao;
import com.techelevator.model.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartService {
    private final CartItemDao cartItemDao;
    private final TaxService taxService;

    @Autowired
    public CartService(CartItemDao cartItemDao, TaxService taxService) {
        this.cartItemDao = cartItemDao;
        this.taxService = taxService;
    }

    //with tax
    public BigDecimal getCartTotalWithTax(int userId, String stateCode) {

        List<CartItem> cartItems = cartItemDao.getCartByUserId(userId);
        BigDecimal subtotal = calculateCartSubtotal(cartItems);

        BigDecimal taxRate = taxService.getTaxRate(stateCode);
        BigDecimal taxAmount = subtotal.multiply(taxRate);

        return subtotal.add(taxAmount);
    }


    //get sub
    public BigDecimal getCartSubtotal(int userId) {
        List<CartItem> cartItems = cartItemDao.getCartByUserId(userId);
        return calculateCartSubtotal(cartItems);
    }

    private BigDecimal calculateCartSubtotal(List<CartItem> cartItems) {
        BigDecimal subtotal = BigDecimal.ZERO;

        for (CartItem item : cartItems) {
            BigDecimal itemTotal = item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            subtotal = subtotal.add(itemTotal);
        }

        return subtotal;
    }
}

