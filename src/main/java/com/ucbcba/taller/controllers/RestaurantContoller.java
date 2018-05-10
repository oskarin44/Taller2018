package com.ucbcba.taller.controllers;


import com.ucbcba.taller.entities.Restaurant;
import com.ucbcba.taller.services.RestaurantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RestaurantContoller {

    private RestaurantService restaurantService;


    @RequestMapping(value = "/restaurant", method = RequestMethod.POST)
    String save(Restaurant restaurant) {

        restaurantService.saveRestaurant(restaurant);
        return "redirect:/bienvenidos";
    }

    @RequestMapping("/newRestaurant")
    String newRestaurant() {


        return "newRestaurant";
    }

    @RequestMapping("/showRestaurant")
    String showRestaurant() {


        return "showRestaurants";

    }

}
