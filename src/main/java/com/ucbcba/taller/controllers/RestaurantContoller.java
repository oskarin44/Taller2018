package com.ucbcba.taller.controllers;


import com.sun.org.apache.xpath.internal.operations.Mod;
import com.ucbcba.taller.entities.*;
import com.ucbcba.taller.services.CategoryService;
import com.ucbcba.taller.services.CityService;
import com.ucbcba.taller.services.RestaurantService;
import com.ucbcba.taller.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RestaurantContoller {

    private RestaurantService restaurantService;
    private CategoryService categoryService;
    private CityService cityService;

    @Autowired
    private UserService userService;

    @Autowired
    public void setRestaurantService(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Autowired
    public void setCityService(CityService cityService) {
        this.cityService = cityService;
    }

    @RequestMapping(value = "/restaurant", method = RequestMethod.POST)
    String save(Restaurant restaurant) {

        restaurantService.saveRestaurant(restaurant);
        return "redirect:/Restaurants";
    }

    @RequestMapping("/newRestaurant")
    String newRestaurant(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        if(user.isAdmin()) {
            Iterable<Category> categories = categoryService.listAllCategories();
            model.addAttribute("categories", categories);
            Iterable<City> cities = cityService.listAllCities();
            model.addAttribute("cities", cities);
            return "newRestaurant";
        }
        else {
            return "redirect:/Restaurants";
        }
    }

    @RequestMapping(value = "/Restaurants",method = RequestMethod.GET)
    public String showRest(Model model) {
        Iterable<Restaurant> restList = restaurantService.listAllRestaurants();
        model.addAttribute("restList",restList);
        return "showRestaurants";
    }

    @RequestMapping("/showRestaurant/{id}")
    String showRes(@PathVariable Integer id, Model model) {
        Restaurant rest = restaurantService.getRestaurant(id);
        model.addAttribute("rest", rest);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        model.addAttribute("comment",new Comment(rest,user));
        return "showRestaurant";
    }

    @RequestMapping("/deleteRestaurant/{id}")
    String delete(@PathVariable Integer id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        if(user.isAdmin()) {
            restaurantService.deleteRestaurant(id);
        }
        return "redirect:/Restaurants";
    }

    @RequestMapping("/editRestaurant/{id}")
    String editPost(@PathVariable Integer id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        if(user.isAdmin()) {
            Restaurant rest = restaurantService.getRestaurant(id);
            Iterable<Category> categories = categoryService.listAllCategories();
            model.addAttribute("categories", categories);
            Iterable<City> cities = cityService.listAllCities();
            model.addAttribute("cities", cities);
            model.addAttribute("rest", rest);
            return "editRestaurant";
        }
        else{
            return "redirect:/Restaurants";
        }
    }
}
