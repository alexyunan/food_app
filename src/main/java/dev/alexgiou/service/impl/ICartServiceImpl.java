package dev.alexgiou.service.impl;

import dev.alexgiou.model.cart.Cart;
import dev.alexgiou.model.cart.CartItem;
import dev.alexgiou.model.food.Food;
import dev.alexgiou.model.user.User;
import dev.alexgiou.repository.CartItemRepository;
import dev.alexgiou.repository.CartRepository;
import dev.alexgiou.request.AddCartItemRequest;
import dev.alexgiou.service.ICartService;
import dev.alexgiou.service.IFoodService;
import dev.alexgiou.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ICartServiceImpl implements ICartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final IUserService userService;
    private final IFoodService foodService;

    @Override
    public CartItem addItemToCart(AddCartItemRequest request, String token) throws Exception {
        User user = userService.findUserByToken(token);

        Food food = foodService.findFoodById(request.getFoodId());

        Cart cart = cartRepository.findByCustomerId(user.getId());

        for (CartItem item : cart.getItems()) {
            if (item.getFood().equals(food)) {
                int newQuantity = item.getQuantity() + request.getQuantity();
                return updateCartItemQuantity(item.getId(), newQuantity);
            }
        }

        CartItem newCartItem = new CartItem();
        newCartItem.setFood(food);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(request.getQuantity());
        newCartItem.setIngredients(request.getIngredients());
        newCartItem.setTotalPrice(request.getQuantity() * food.getPrice());

        CartItem savedCartItem = cartItemRepository.save(newCartItem);
        cart.getItems().add(savedCartItem);

        return savedCartItem;
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(
                () -> new RuntimeException("cart item not found")
        );
        cartItem.setQuantity(quantity);

        // quantity * price = totalPrice
        cartItem.setTotalPrice(cartItem.getFood().getPrice() * quantity);

        return cartItemRepository.save(cartItem);
    }

    @Override
    public Cart removeItemFromCart(Long cartItemId, String token) throws Exception {
        User user = userService.findUserByToken(token);

        Cart cart = cartRepository.findByCustomerId(user.getId());

        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(
                () -> new RuntimeException("cart item not found")
        );

        cart.getItems().remove(cartItem);

        return cartRepository.save(cart);
    }

    @Override
    public Long calculateCartTotals(Cart cart) {
        long total = 0L;

        for (CartItem item : cart.getItems()) {
            total += item.getFood().getPrice() * item.getQuantity();
        }
        return total;
    }

    @Override
    public Cart findCartById(Long cartId) {
        return cartRepository.findById(cartId).orElseThrow(
                () -> new RuntimeException("cart  with id -> %s not found".formatted(cartId))
        );

    }

    @Override
    public Cart findCartByUserId(Long userId) {
        return cartRepository.findByCustomerId(userId);
    }

    @Override
    public Cart clearCart(Long userId) {
        Cart cart = findCartByUserId(userId);
        cart.getItems().clear();
        return cartRepository.save(cart);
    }
}
