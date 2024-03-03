package dev.alexgiou.controller;

import dev.alexgiou.model.order.Order;
import dev.alexgiou.model.user.User;
import dev.alexgiou.request.OrderRequest;
import dev.alexgiou.service.IOrderService;
import dev.alexgiou.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {

    private final IOrderService orderService;
    private final IUserService userService;

    @PostMapping("/order")
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest request,
                                             @RequestHeader("Authorization") String token) {

        try {
            User user = userService.findUserByToken(token);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            Order order = orderService.createOrder(request, user);
            return new ResponseEntity<>(order, HttpStatus.CREATED);

        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/order/user")
    public ResponseEntity<List<Order>> getOrderHistory(@RequestHeader("Authorization") String token) throws Exception {

        User user = userService.findUserByToken(token);
        List<Order> order = orderService.getUsersOrder(user.getId());

        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }
}
