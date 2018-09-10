package com.example.sabari.hostel_app;

public class Item {

    String animalName;
    String animalImage;

    public Item(String animalName,String animalImage)
    {
        this.animalImage=animalImage;
        this.animalName=animalName;
    }
    public String getAnimalName()
    {
        return animalName;
    }
    public String getAnimalImage()
    {
        return animalImage;
    }
}