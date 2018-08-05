package com.shaan.retrofittutorial;

import com.shaan.retrofittutorial.Model.Contact;
import com.shaan.retrofittutorial.Model.Food;
import com.shaan.retrofittutorial.Model.ImageFile;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @POST("read_contacts.php")
    Call<List<Contact>> getContacts();

    @GET("calories.php")
    Call<List<Food>> getFoods(@Query("item_type") String itemType);

    @FormUrlEncoded
    @POST("upload_image.php")
    Call<ImageFile> uploadImage(@Field("title") String title, @Field("image") String image);
}
