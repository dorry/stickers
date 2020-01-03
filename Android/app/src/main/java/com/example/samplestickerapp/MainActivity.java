package com.example.samplestickerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import model.Category;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import service.CategoryClient;
import service.RetrofitBuilder;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private CategoryAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context = this;

        Retrofit retrofit = RetrofitBuilder.build();
        CategoryClient client = retrofit.create(CategoryClient.class);
        Call<List<Category>> call = client.allCategories();
//        ArrayList<Category> typeList = new ArrayList<>();
//        typeList.add(new CategoryItem(R.drawable.tray_cuppy,"Cuppy"));

        mRecyclerView = findViewById(R.id.typesRecyclerView);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(this, 2);


        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                List<Category> categoryList = response.body();
                mAdapter = new CategoryAdapter(categoryList, new IOnCategoryClickListener() {
                    @Override
                    public void onClick(Category category) {
//                        Toast.makeText(MainActivity.this, ""+category.getId(), Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(MainActivity.this, AssetsDownloaderActivity.class);
                        i.putExtra("id",category.getId());
                        startActivity(i);
                    }
                });
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                Log.d("MainActivity", "onFailure: "+t.toString());
            }
        });


    }


}
