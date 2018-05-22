package com.ucbcba.taller.controllers;


import com.sun.org.apache.xpath.internal.operations.Mod;
import com.ucbcba.taller.entities.Category;
import com.ucbcba.taller.entities.City;
import com.ucbcba.taller.entities.User;
import com.ucbcba.taller.entities.Restaurant;
import com.ucbcba.taller.services.CategoryService;
import com.ucbcba.taller.services.CityService;
import com.ucbcba.taller.services.RestaurantService;
import com.ucbcba.taller.services.UploadFileService;
import com.ucbcba.taller.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.AttributedString;

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

    @Autowired
    private UploadFileService uploadFileService;


    @RequestMapping(value = "/restaurant", method = RequestMethod.POST)
    String save(@RequestParam("file")MultipartFile file,Restaurant restaurant) {

        try {

            uploadFileService.saveFile(file,restaurant.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }

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
        return "showRestaurant";
    }

    @RequestMapping("/showPublicRestaurant/{id}")
    String showAllRest(@PathVariable Integer id, Model model) {
        Restaurant rest = restaurantService.getRestaurant(id);
        model.addAttribute("rest", rest);
        return "showPublicRestaurant";
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

    @RequestMapping("/like/{id}")
    String like(@PathVariable Integer id) {
        Restaurant res = restaurantService.getRestaurant(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        if (res.findUserLike(user) == false) {
            res.addLikke(user);
            res.setLikes(res.getLikes()+1);
            restaurantService.saveRestaurant(res);
        }
        return "redirect:/showRestaurant/" + res.getId();
    }

}
