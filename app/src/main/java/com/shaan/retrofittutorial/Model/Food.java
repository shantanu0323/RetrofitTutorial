package com.shaan.retrofittutorial.Model;

import com.google.gson.annotations.SerializedName;

public class Food {

    @SerializedName("name")
    private String name;

    @SerializedName("image_path")
    private String imagePath;

    @SerializedName("calories")
    private String calories;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }
}
