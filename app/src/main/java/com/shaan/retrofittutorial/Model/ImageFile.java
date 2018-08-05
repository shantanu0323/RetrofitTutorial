package com.shaan.retrofittutorial.Model;

import com.google.gson.annotations.SerializedName;

public class ImageFile {

    @SerializedName("title")
    String title;

    @SerializedName("image")
    String image;

    @SerializedName("response")
    String response;

    public String getResponse() {
        return response;
    }

}
