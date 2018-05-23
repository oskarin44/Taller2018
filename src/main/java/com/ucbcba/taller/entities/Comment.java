package com.ucbcba.taller.entities;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Size(min = 1,max = 50,message = "comentario no exceda de 50 palabras")
    private String text;

    @ManyToOne
    @JoinColumn(name= "restauran_id")
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public Comment(Restaurant restaurant, User user)
    {
        this.restaurant=restaurant;
        this.user=user;
    }

    public Integer getId(){return id;}

    public void setId(Integer id){this.id=id;}

    public String getText(){return text;}

    public void setText(String text){this.text=text;}

    public Restaurant getRestaurant(){return restaurant;}

    public void setRestaurant(Restaurant restaurant){this.restaurant=restaurant;}

    public User getUser(){return user;}

    public void setUser(User user){this.user=user;}
}
