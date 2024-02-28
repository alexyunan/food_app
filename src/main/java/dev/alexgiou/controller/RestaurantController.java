package dev.alexgiou.controller;

import dev.alexgiou.dto.RestaurantDto;
import dev.alexgiou.model.Restaurant;
import dev.alexgiou.model.user.User;
import dev.alexgiou.service.IRestaurantService;
import dev.alexgiou.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final IRestaurantService restaurantService;
    private final IUserService userService;

    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> searchRestaurant(
            @RequestHeader("Authorization") String token,
            @RequestParam String keyword) throws Exception {

        User user = userService.findUserByToken(token);
        List<Restaurant> restaurant = restaurantService.searchRestaurant(keyword);

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Restaurant>> getAllRestaurant(
            @RequestHeader("Authorization") String token) throws Exception {

        User user = userService.findUserByToken(token);
        List<Restaurant> restaurant = restaurantService.getAllRestaurant();

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> findRestaurantById(
            @RequestHeader("Authorization") String token,
            @PathVariable("id") Long restaurantId) throws Exception {

        User user = userService.findUserByToken(token);
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @PutMapping("/{id}/add-favorites")
    public ResponseEntity<RestaurantDto> addToFavorites(
            @RequestHeader("Authorization") String token,
            @PathVariable("id") Long restaurantId) throws Exception {

        User user = userService.findUserByToken(token);
        RestaurantDto restaurant = restaurantService.addToFavorites(restaurantId, user);


        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
}
