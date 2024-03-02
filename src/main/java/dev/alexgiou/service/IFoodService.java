package dev.alexgiou.service;

import dev.alexgiou.model.Restaurant;
import dev.alexgiou.model.food.Category;
import dev.alexgiou.model.food.Food;
import dev.alexgiou.request.CreateFoodRequest;

import java.util.List;

public interface IFoodService {

    Food createFood(CreateFoodRequest request, Category category, Restaurant restaurant);

    void deleteFood(Long foodId);

    List<Food> getRestaurantFood(Long restaurantId, boolean isVegetarian, boolean isNonVegeterian, boolean isSeasonal, String foodCategory);

    List<Food> searchFood(String keyword);

    Food findFoodById(Long foodId);

    Food updateAvailabilityStatus(Long foodId);
}
