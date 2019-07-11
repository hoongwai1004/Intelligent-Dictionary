package com.cat300.ty_ch.intelligentdictionary;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Favourites extends AppCompatActivity {

    private RecyclerView.LayoutManager favouriteLayoutManager;
    private List<String> favouriteData = new ArrayList<>();
    private List<String> reversaData = new ArrayList<>();
    private FavoriteDBHelper favoriteDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        favoriteDBHelper = new FavoriteDBHelper(this);
        //setupFavouriteList();
        populateFavoriteList();

        RecyclerView favouriteRecyclerView = (RecyclerView)findViewById(R.id.favouriteView);
        favouriteRecyclerView.setHasFixedSize(true);
        favouriteLayoutManager = new LinearLayoutManager(this);
        favouriteRecyclerView.setLayoutManager(favouriteLayoutManager);
        FavouriteAdapter favouriteAdapter = new FavouriteAdapter(favouriteData, this);
        favouriteRecyclerView.setAdapter(favouriteAdapter);
    }

    private void populateFavoriteList() {
        Cursor data = favoriteDBHelper.getAllData();
        while(data.moveToNext())  {
            reversaData.add(data.getString(1));
        }

        for(int i = reversaData.size() - 1; i >= 0; i--)
            favouriteData.add(reversaData.get(i));
    }
}
