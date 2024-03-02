package dev.alexgiou.repository;

import dev.alexgiou.model.food.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findCategoryByRestaurantId(Long restaurantId);
}
