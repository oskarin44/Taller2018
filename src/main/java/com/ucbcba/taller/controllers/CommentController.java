package com.ucbcba.taller.controllers;


import com.ucbcba.taller.entities.Comment;
import com.ucbcba.taller.services.CommentService;
import com.ucbcba.taller.services.RestaurantService;
import com.ucbcba.taller.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class CommentController {
    private CommentService commentService;
    private UserService userService;
    private RestaurantService restaurantService;

    @Autowired
    public void setCommentService(CommentService commentService){this.commentService=commentService;}

    @Autowired
    public void setUserService(UserService userService){this.userService=userService;}

    @Autowired
    public void setRestaurantService(RestaurantService restaurantService){this.restaurantService=restaurantService;}

    @RequestMapping(value="/comment", method = RequestMethod.POST)
    String save(Model model, @Valid Comment comment, BindingResult bindingResult) {
comment.getUser().getId();
        /*if(bindingResult.hasErrors()) {
            Restaurant restaurant = restaurantService.getRestaurant(comment.getRestaurant().getId());
            model.addAttribute("rest", restaurant);
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findByUsername(auth.getName());
            model.addAttribute("user", user);
            return "showRestaurant";
        }*/
        commentService.saveComment(comment);

        return "redirect:/showRestaurant/"+comment.getRestaurant().getId();
    }
}
