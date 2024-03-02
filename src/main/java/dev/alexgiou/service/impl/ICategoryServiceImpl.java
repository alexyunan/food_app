package dev.alexgiou.service.impl;

import dev.alexgiou.model.Restaurant;
import dev.alexgiou.model.food.Category;
import dev.alexgiou.repository.CategoryRepository;
import dev.alexgiou.service.ICategoryService;
import dev.alexgiou.service.IRestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ICategoryServiceImpl implements ICategoryService {

    private final CategoryRepository categoryRepository;
    private final IRestaurantService restaurantService;

    @Override
    public Category createCategory(String name, Long userId) throws Exception {
        Restaurant restaurant = restaurantService.getRestaurantByUserId(userId);
        Category category = new Category();
        category.setName(name);
        category.setRestaurant(restaurant);

        return categoryRepository.save(category);

    }

    @Override
    public List<Category> findCategoryByRestaurantId(Long restaurantId) throws Exception {
        Restaurant restaurant = restaurantService.getRestaurantByUserId(restaurantId);
        return categoryRepository.findCategoryByRestaurantId(restaurant.getId());
    }

    @Override
    public Category findCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(
                () -> new RuntimeException("category with id -> %s not found".formatted(categoryId))
        );
    }
}
