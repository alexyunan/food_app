package dev.alexgiou.service;

import dev.alexgiou.dto.RestaurantDto;
import dev.alexgiou.model.Restaurant;
import dev.alexgiou.model.user.User;
import dev.alexgiou.request.CreateRestaurantRequest;

import java.util.List;

public interface IRestaurantService {

    Restaurant createRestaurant(CreateRestaurantRequest request, User user);

    Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest request);

    void deleteRestaurant(Long restaurantId);

    List<Restaurant> getAllRestaurant();

    List<Restaurant> searchRestaurant();

    Restaurant findRestaurantById(Long restaurantId);

    Restaurant getRestaurantByUserId(Long userId);

    RestaurantDto addToFavorites(Long restaurantId, User user);

    Restaurant updateRestaurantStatus(Long restaurantId);
}
