package dev.alexgiou.controller;

import dev.alexgiou.model.IngredientCategory;
import dev.alexgiou.model.IngredientsItem;
import dev.alexgiou.request.IngredientCategoryRequest;
import dev.alexgiou.request.IngredientItemRequest;
import dev.alexgiou.service.IIngredientsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/ingredients")
@RequiredArgsConstructor
public class IngredientController {

    private final IIngredientsService ingredientsService;

    @PostMapping("/category")
    public ResponseEntity<IngredientCategory> createIngredientCategory(@RequestBody IngredientCategoryRequest request) throws Exception {
        IngredientCategory item = ingredientsService.createIngredientCategory(request.getName(), request.getRestaurantId());

        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PostMapping()
    public ResponseEntity<IngredientsItem> createIngredientItem(@RequestBody IngredientItemRequest request) throws Exception {
        IngredientsItem item = ingredientsService.createIngredientItem(request.getRestaurantId(), request.getName(), request.getCategoryId());

        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }


    @PutMapping("/{id}/stoke")
    public ResponseEntity<IngredientsItem> updateIngredientStock(@PathVariable("id") Long id) throws Exception {
        IngredientsItem item = ingredientsService.updateStock(id);

        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<IngredientsItem>> getRestaurantIngredients(@PathVariable("id") Long restaurantId) {
        List<IngredientsItem> items = ingredientsService.findRestaurantIngredients(restaurantId);

        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}/category")
    public ResponseEntity<List<IngredientCategory>> getRestaurantIngredientsCategory(@PathVariable("id") Long restaurantId) throws Exception {
        List<IngredientCategory> items = ingredientsService.findIngredientCategoryByRestaurantId(restaurantId);

        return new ResponseEntity<>(items, HttpStatus.OK);
    }
}
