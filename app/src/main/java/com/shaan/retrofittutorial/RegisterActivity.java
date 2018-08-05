package com.shaan.retrofittutorial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    private static final String BASE_URL = "http://192.168.137.1:80/api/";
    EditText etName, etEmail, etTopics, etAge;
    Button bCreate;
    private static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findViews();
        bCreate.setOnClickListener(event -> {
            User user = new User(
                    etName.getText().toString().trim(),
                    etEmail.getText().toString().trim(),
                    etAge.getText().toString().trim(),
                    etTopics.getText().toString().trim());


            sendNetworkRequest(user);
        });

    }

    private void sendNetworkRequest(User user) {
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (BuildConfig.DEBUG) {
            okHttpBuilder.addInterceptor(loggingInterceptor);
        }

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .client(okHttpBuilder.build());
        Retrofit retrofit = builder.build();

        UserClient userClient = retrofit.create(UserClient.class);
        Call<User> responseUser = userClient.createAccWithBody(user);
        responseUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Name = " + Objects.requireNonNull(response.body()).getName() +
                            " : Id = " + Objects.requireNonNull(response.body()).getId(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "Unsucessfull " + Objects.requireNonNull(response.errorBody()).toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Unable to create account" + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

    private void findViews() {
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etTopics = findViewById(R.id.etTopics);
        etAge = findViewById(R.id.etAge);
        bCreate = findViewById(R.id.bCreate);
    }
}
