package com.ucbcba.taller.controllers;


import com.ucbcba.taller.entities.City;
import com.ucbcba.taller.entities.User;
import com.ucbcba.taller.services.CityService;
import com.ucbcba.taller.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CityController {
    private CityService cityService;

    @Autowired
    private UserService userService;

    @Autowired
    public void setCityService (CityService cityService){
        this.cityService=cityService;
    }

    @RequestMapping(value = "/city", method = RequestMethod.POST)
    String save(City city) {

        cityService.saveCity(city);
        return "redirect:/Cities";
    }

    @RequestMapping("/newCity")
    String newCity(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        if(user.isAdmin()) {
            Iterable<City> cities = cityService.listAllCities();
            model.addAttribute("cities", cities);
            return "newCity";
        }
        else{
            return "redirect:/Cities";
        }
    }

    @RequestMapping(value = "/Cities" ,method = RequestMethod.GET)
    String showCities(Model model) {
        Iterable<City> cities = cityService.listAllCities();
        model.addAttribute("cities", cities);
        return "showCities";
    }

    @RequestMapping("/showCity/{id}")
    String showCity(@PathVariable Integer id, Model model) {
        City city = cityService.getCity(id);
        model.addAttribute("city", city);
        return "showCity";
    }


    @RequestMapping("/deleteCity/{id}")
    String deleteCity(@PathVariable Integer id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        if(user.isAdmin()) {
            cityService.deleteCity(id);
        }
        return "redirect:/Cities";
    }

    @RequestMapping("/editCity/{id}")
    String editCity(@PathVariable Integer id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        if(user.isAdmin()) {
            City city = cityService.getCity(id);
            model.addAttribute("city", city);
            return "editCity";  //****
        }
        else{
            return "redirect:/Cities";  //****
        }
    }
}
