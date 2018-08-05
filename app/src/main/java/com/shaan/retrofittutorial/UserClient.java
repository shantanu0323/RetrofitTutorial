package com.shaan.retrofittutorial;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

public interface UserClient {
    @GET()
    Call<String> createAccount(@Url String url);

    @POST("post.php")
    Call<User> createAccWithBody(@Body User user);

    @Multipart
    @POST("upload.php")
    Call<ResponseBody> uploadFile(
            @Part("description") RequestBody description,
            @Part MultipartBody.Part photo);
}
