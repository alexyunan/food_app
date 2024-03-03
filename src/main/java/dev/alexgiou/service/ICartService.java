package dev.alexgiou.service;

import dev.alexgiou.model.cart.Cart;
import dev.alexgiou.model.cart.CartItem;
import dev.alexgiou.request.AddCartItemRequest;

public interface ICartService {

    CartItem addItemToCart(AddCartItemRequest request, String token) throws Exception;

    CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception;

    Cart removeItemFromCart(Long cartItemId, String token) throws Exception;

    Long calculateCartTotals(Cart cart);

    Cart findCartById(Long cartId);

    Cart findCartByUserId(Long userId);

    Cart clearCart(Long userId);
}
