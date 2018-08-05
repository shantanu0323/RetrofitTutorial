package com.shaan.retrofittutorial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GithubActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://api.github.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github);

        final ListView listView = findViewById(R.id.listView);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        GithubClient client = retrofit.create(GithubClient.class);
        Call<List<GithubRepo>> call = client.reposForUser("shantanu0323");
        call.enqueue(new Callback<List<GithubRepo>>() {
            @Override
            public void onResponse(Call<List<GithubRepo>> call, Response<List<GithubRepo>> response) {
                List<GithubRepo> repos = response.body();
                listView.setAdapter(new GithubRepoAdapter(GithubActivity.this, repos));
            }

            @Override
            public void onFailure(Call<List<GithubRepo>> call, Throwable t) {
                Toast.makeText(GithubActivity.this, "Problem with Internet Connectivity", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
