package dev.alexgiou.controller;

import dev.alexgiou.model.Restaurant;
import dev.alexgiou.model.food.Food;
import dev.alexgiou.model.user.User;
import dev.alexgiou.request.CreateFoodRequest;
import dev.alexgiou.response.MessageResponse;
import dev.alexgiou.service.IFoodService;
import dev.alexgiou.service.IRestaurantService;
import dev.alexgiou.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/food")
@RequiredArgsConstructor
public class AdminFoodController {

    private final IFoodService foodService;
    private final IUserService userService;
    private final IRestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest request,
                                           @RequestHeader("Authorization") String token) {
        try {
            User user = userService.findUserByToken(token);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            Restaurant restaurant = restaurantService.findRestaurantById(request.getRestaurantId());
            if (restaurant == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }


            Food food = foodService.createFood(request, request.getCategory(), restaurant);
            return new ResponseEntity<>(food, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteFood(@PathVariable("id") Long foodId,
                                                      @RequestHeader("Authorization") String token) {

        try {
            User user = userService.findUserByToken(token);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }


            foodService.deleteFood(foodId);

            MessageResponse message = new MessageResponse();
            message.setMessage("Food deleted successfully");
            return new ResponseEntity<>(message, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Food> updateFoodAvailabilityStatus(@PathVariable("id") Long foodId,
                                                             @RequestHeader("Authorization") String token) {

        try {
            User user = userService.findUserByToken(token);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            
            Food food = foodService.updateAvailabilityStatus(foodId);

            return new ResponseEntity<>(food, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
