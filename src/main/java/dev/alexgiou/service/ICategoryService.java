package dev.alexgiou.service;

import dev.alexgiou.model.food.Category;

import java.util.List;

public interface ICategoryService {

    Category createCategory(String name, Long userId) throws Exception;

    List<Category> findCategoryByRestaurantId(Long restaurantId) throws Exception;

    Category findCategoryById(Long categoryId);


}
