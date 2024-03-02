package dev.alexgiou.request;

import dev.alexgiou.model.IngredientsItem;
import dev.alexgiou.model.food.Category;
import lombok.Data;

import java.util.List;

@Data
public class CreateFoodRequest {

    private String name;
    private String description;
    private Long price;
    private Category category;
    private List<String> images;
    private Long restaurantId;
    private boolean isVegetarian;
    private boolean isSeasonal;
    private List<IngredientsItem> ingredients;
}
