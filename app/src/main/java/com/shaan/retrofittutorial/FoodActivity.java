package com.shaan.retrofittutorial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shaan.retrofittutorial.Adapters.FoodRecyclerAdapter;
import com.shaan.retrofittutorial.Model.Food;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FoodActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Food> foods;
    private FoodRecyclerAdapter adapter;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        if (getIntent().getExtras() != null) {
            String type = getIntent().getExtras().getString("type");
            fetchInformation(type);
        }
    }

    public void fetchInformation(String type) {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Food>> call = apiInterface.getFoods(type);

        call.enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                foods = response.body();
                adapter = new FoodRecyclerAdapter(FoodActivity.this,foods);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {

            }
        });
    }
}
