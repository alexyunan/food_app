package dev.alexgiou.controller;

import dev.alexgiou.model.order.Order;
import dev.alexgiou.model.user.User;
import dev.alexgiou.service.IOrderService;
import dev.alexgiou.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminOrderController {

    private final IOrderService orderService;
    private final IUserService userService;

    @GetMapping("/order/restaurant/{id}")
    public ResponseEntity<List<Order>> getOrderHistory(@PathVariable("id") Long restaurantId,
                                                       @RequestParam(required = false) String orderStatus,
                                                       @RequestHeader("Authorization") String token) throws Exception {

        try {
            User user = userService.findUserByToken(token);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            List<Order> order = orderService.getRestaurantsOrder(restaurantId, orderStatus);

            return new ResponseEntity<>(order, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/order/{id}/{orderStatus}")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable("id") Long orderId,
                                                   @PathVariable String orderStatus,
                                                   @RequestHeader("Authorization") String token) throws Exception {

        try {
            User user = userService.findUserByToken(token);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            Order order = orderService.updateOrder(orderId, orderStatus);

            return new ResponseEntity<>(order, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
