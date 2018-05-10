package com.ucbcba.taller.entities;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Blob;
import java.util.List;

@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private Integer phone;

    @NotNull
    private String description;

    @ManyToOne
    @JoinColumn (name="category_id")
    private Category category;

    /*@OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    List<City> cities;*/

    //@NotNull
    private Blob photo;

    public Integer getId(){return id;}

    public void setId(Integer id){this.id=id;}

    public String getName(){return name;}

    public void setName(String name){this.name=name;}

    public Integer getPhone(){return phone;}

    public void setPhone(Integer phone){this.phone=phone;}

    public String getDescription(){return description;}

    public void setDescription(String description){this.description=description;}

    public Category getCategory(){return  category;}

    public void setCategory(Category category){this.category=category;}

   /* public List<City> getCities(){return this.cities;}

    public void setCities(List<City> cities){this.cities=cities;}*/

}
