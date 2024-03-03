package dev.alexgiou.service;

import dev.alexgiou.model.order.Order;
import dev.alexgiou.model.user.User;
import dev.alexgiou.request.OrderRequest;

import java.util.List;

public interface IOrderService {

    Order createOrder(OrderRequest request, User user) throws Exception;

    Order updateOrder(Long orderId, String orderStatus) throws Exception;

    void cancelOrder(Long orderId);

    List<Order> getUsersOrder(Long userId);

    List<Order> getRestaurantsOrder(Long restaurantId, String orderStatus);

    Order findOrderById(Long orderId);
}
