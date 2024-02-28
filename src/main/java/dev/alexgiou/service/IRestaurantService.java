package dev.alexgiou.service;

import dev.alexgiou.dto.RestaurantDto;
import dev.alexgiou.model.Restaurant;
import dev.alexgiou.model.user.User;
import dev.alexgiou.request.CreateRestaurantRequest;

import java.util.List;

public interface IRestaurantService {

    Restaurant createRestaurant(CreateRestaurantRequest request, User user);

    Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest request) throws Exception;

    void deleteRestaurant(Long restaurantId) throws Exception;

    List<Restaurant> getAllRestaurant();

    List<Restaurant> searchRestaurant(String keyword);

    Restaurant findRestaurantById(Long restaurantId) throws Exception;

    Restaurant getRestaurantByUserId(Long userId) throws Exception;

    RestaurantDto addToFavorites(Long restaurantId, User user) throws Exception;

    Restaurant updateRestaurantStatus(Long restaurantId) throws Exception;
}
