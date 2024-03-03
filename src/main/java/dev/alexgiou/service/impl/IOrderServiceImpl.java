package dev.alexgiou.service.impl;

import dev.alexgiou.model.Address;
import dev.alexgiou.model.Restaurant;
import dev.alexgiou.model.cart.Cart;
import dev.alexgiou.model.cart.CartItem;
import dev.alexgiou.model.order.Order;
import dev.alexgiou.model.order.OrderItem;
import dev.alexgiou.model.user.User;
import dev.alexgiou.repository.AddressRepository;
import dev.alexgiou.repository.OrderItemRepository;
import dev.alexgiou.repository.OrderRepository;
import dev.alexgiou.repository.UserRepository;
import dev.alexgiou.request.OrderRequest;
import dev.alexgiou.service.ICartService;
import dev.alexgiou.service.IOrderService;
import dev.alexgiou.service.IRestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IOrderServiceImpl implements IOrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final IRestaurantService restaurantService;
    private final ICartService cartService;

    @Override
    public Order createOrder(OrderRequest request, User user) throws Exception {

        Address address = request.getAddress();

        Address saveAddress = addressRepository.save(address);

        if (!user.getAddresses().contains(saveAddress)) {
            user.getAddresses().add(saveAddress);
            userRepository.save(user);
        }

        Restaurant restaurant = restaurantService.findRestaurantById(request.getRestaurantId());

        Order createdOrder = new Order();
        createdOrder.setCustomer(user);
        createdOrder.setCreatedAt(LocalDateTime.now());
        createdOrder.setOrderStatus("PENDING");
        createdOrder.setDeliveryAddress(saveAddress);
        createdOrder.setRestaurant(restaurant);

        Cart cart = cartService.findCartByUserId(user.getId());

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setFood(cartItem.getFood());
            orderItem.setIngredients(cartItem.getIngredients());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(cartItem.getTotalPrice());

            OrderItem savedOrderItem = orderItemRepository.save(orderItem);
            orderItems.add(savedOrderItem);
        }

        Long totalPrice = cartService.calculateCartTotals(cart);

        createdOrder.setOrderItems(orderItems);
        createdOrder.setTotalPrice(totalPrice);

        Order savedOrder = orderRepository.save(createdOrder);
        restaurant.getOrders().add(savedOrder);

        return createdOrder;
    }

    @Override
    public Order updateOrder(Long orderId, String orderStatus) throws Exception {
        Order order = findOrderById(orderId);

        if (isValidOrderStatus(orderStatus)) {
            order.setOrderStatus(orderStatus);
            return orderRepository.save(order);
        }

        throw new Exception("Please select a valid order status");
    }

    @Override
    public void cancelOrder(Long orderId) {
        Order order = findOrderById(orderId);
        orderRepository.delete(order);
    }

    @Override
    public List<Order> getUsersOrder(Long userId) {
        return orderRepository.findByCustomerId(userId);
    }

    @Override
    public List<Order> getRestaurantsOrder(Long restaurantId, String orderStatus) {
        List<Order> orders = orderRepository.findByRestaurantId(restaurantId);
        if (orderStatus != null) {
            orders = orders.stream().filter(order -> order.getOrderStatus().equals(orderStatus)).toList();
        }
        return orders;
    }

    @Override
    public Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(
                () -> new RuntimeException("order with id -> %s not found.".formatted(orderId))
        );
    }

    private boolean isValidOrderStatus(String orderStatus) {
        List<String> validStatusList = Arrays.asList("OUT_FOR_DELIVERY", "DELIVERED", "COMPLETED", "PENDING");
        return validStatusList.contains(orderStatus);
    }
}
