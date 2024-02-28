package dev.alexgiou.controller;

import dev.alexgiou.model.Restaurant;
import dev.alexgiou.model.user.User;
import dev.alexgiou.request.CreateRestaurantRequest;
import dev.alexgiou.response.MessageResponse;
import dev.alexgiou.service.IRestaurantService;
import dev.alexgiou.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin/restaurants")
@RequiredArgsConstructor
public class AdminRestaurantController {

    private final IRestaurantService restaurantService;
    private final IUserService userService;

    @PostMapping()
    public ResponseEntity<Restaurant> createRestaurant(
            @RequestBody CreateRestaurantRequest request,
            @RequestHeader("Authorization") String token) throws Exception {

        User user = userService.findUserByToken(token);

        Restaurant restaurant = restaurantService.createRestaurant(request, user);

        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(
            @RequestBody CreateRestaurantRequest request,
            @RequestHeader("Authorization") String token,
            @PathVariable("id") Long restaurantId) throws Exception {

        User user = userService.findUserByToken(token);

        Restaurant restaurant = restaurantService.updateRestaurant(restaurantId, request);

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteRestaurant(
            @RequestHeader("Authorization") String token,
            @PathVariable("id") Long restaurantId) throws Exception {

        User user = userService.findUserByToken(token);

        restaurantService.deleteRestaurant(restaurantId);

        MessageResponse response = new MessageResponse();
        response.setMessage("restaurant deleted successfully.");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Restaurant> updateRestaurantStatus(
            @RequestHeader("Authorization") String token,
            @PathVariable("id") Long restaurantId) throws Exception {

        User user = userService.findUserByToken(token);

        Restaurant restaurant = restaurantService.updateRestaurantStatus(restaurantId);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<Restaurant> findRestaurantUserId(
            @RequestHeader("Authorization") String token) throws Exception {

        User user = userService.findUserByToken(token);
        Restaurant restaurant = restaurantService.getRestaurantByUserId(user.getId());

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }


}
