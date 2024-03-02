package dev.alexgiou.controller;

import dev.alexgiou.model.food.Category;
import dev.alexgiou.model.user.User;
import dev.alexgiou.service.ICategoryService;
import dev.alexgiou.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/category")
@RequiredArgsConstructor
public class CategoryController {

    private final ICategoryService categoryService;
    private final IUserService userService;

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category,
                                                   @RequestHeader("Authorization") String token) {

        try {
            User user = userService.findUserByToken(token);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            Category createdCategory = categoryService.createCategory(category.getName(), user.getId());
            return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);

        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/restaurant")
    public ResponseEntity<List<Category>> getRestaurantCategory(@RequestHeader("Authorization") String token) {

        try {
            User user = userService.findUserByToken(token);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            List<Category> categories = categoryService.findCategoryByRestaurantId(user.getId());
            return new ResponseEntity<>(categories, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> findCategoryById(@PathVariable("id") Long categoryId,
                                                     @RequestHeader("Authorization") String token) {
        try {
            User user = userService.findUserByToken(token);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            Category category = categoryService.findCategoryById(categoryId);
            return new ResponseEntity<>(category, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
