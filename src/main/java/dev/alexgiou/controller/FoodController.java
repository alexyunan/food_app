package dev.alexgiou.controller;

import dev.alexgiou.model.food.Food;
import dev.alexgiou.model.user.User;
import dev.alexgiou.service.IFoodService;
import dev.alexgiou.service.IRestaurantService;
import dev.alexgiou.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
@RequiredArgsConstructor
public class FoodController {

    private final IFoodService foodService;
    private final IUserService userService;
    private final IRestaurantService restaurantService;

    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(@RequestParam String keyword,
                                                 @RequestHeader("Authorization") String token) {
        try {
            User user = userService.findUserByToken(token);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            List<Food> foods = foodService.searchFood(keyword);
            return new ResponseEntity<>(foods, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<Food>> getRestaurantFood(
            @RequestParam boolean vegeterian,
            @RequestParam boolean seasonal,
            @RequestParam boolean nonVegeterian,
            @RequestParam(required = false) String foodCategory,
            @PathVariable("id") Long restaurantId,
            @RequestHeader("Authorization") String token) {
        try {
            User user = userService.findUserByToken(token);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            List<Food> foods = foodService.getRestaurantFood(restaurantId, vegeterian, nonVegeterian, seasonal, foodCategory);
            return new ResponseEntity<>(foods, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
